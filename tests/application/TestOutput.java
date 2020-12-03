package application;

import java.util.ArrayList;
import java.util.List;

public class TestOutput {
  private final List<String> rows = new ArrayList<>();

  public void print(String row) {
    rows.add(row);
  }

  public List<String> getRows() {
    return rows;
  }
}
