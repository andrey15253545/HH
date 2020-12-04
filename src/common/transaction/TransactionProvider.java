package common.transaction;

/**
 * Интерфейс для транзакционного механизма
 */
public interface TransactionProvider {

    void open();

    void close();

}
