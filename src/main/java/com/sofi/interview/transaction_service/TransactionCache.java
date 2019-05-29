package com.sofi.interview.transaction_service;

import com.sofi.interview.transaction_service.models.Transaction;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import lombok.extern.log4j.Log4j;

@Log4j
public enum TransactionCache {

  INSTANCE;

  public static final int TOP_K = 3;

  // the key is user-id in the 2 maps below
  private static final Map<Integer, List<Transaction>> allTransactions = new HashMap<>();
  private static final Map<Integer, PriorityQueue<Entry<Integer, String>>> topMerchantMap = new HashMap<>();

  public synchronized void add(Transaction transaction) {

    synchronized (this) {
      int userId = transaction.userId();
      List<Transaction> transactionList = allTransactions.getOrDefault(userId, new ArrayList<>());
      transactionList.add(transaction);
      allTransactions.put(userId, transactionList);

      PriorityQueue<Entry<Integer, String>> queue = topMerchantMap.get(userId);
      if (queue == null) {
        queue = new PriorityQueue<>(TOP_K, new Comparator<Entry<Integer, String>>() {
          @Override
          public int compare(Entry<Integer, String> x, Entry<Integer, String> y) {
            return x.getKey() - y.getKey();
          }
        });
        topMerchantMap.put(userId, queue);
      }

      String merchant = transaction.merchant();
      int count = (int) transactionList.stream().filter(x -> x.merchant().equals(merchant)).count();
      queue.removeIf(x -> x.getValue().equals(merchant));
      queue.add(new AbstractMap.SimpleEntry(count, merchant));

      if (queue.size() > TOP_K) {
        queue.poll(); // Limit queue size to TOP_K
      }
    }
  }

  public List<String> getTopMerchats(int userId) {

    List<Transaction> transactions = allTransactions.getOrDefault(userId, null);
    if (transactions == null || transactions.size() < 5) {
      log.error("Insufficient number of transactions have been processed for user " + userId);
      return null;
    }

    List<String> ret = new ArrayList<>();
    Iterator<Entry<Integer, String>> iterator = topMerchantMap.get(userId).iterator();
    while (iterator.hasNext()) {
      ret.add(iterator.next().getValue());
    }

    return ret;
  }
}
