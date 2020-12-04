package application;

import common.transaction.TransactionProvider;

/**
 * Простейшая имплементация транзакционного механизма
 */
public class TestTransactionProvider implements TransactionProvider {
  private final TestOutput output;

  public TestTransactionProvider(TestOutput output) {
    this.output = output;
  }

  @Override
  public void open() {
    output.print("open transaction");
  }

  @Override
  public void close() {
    output.print("close transaction");
  }
}