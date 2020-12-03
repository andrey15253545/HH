
import common.EasyContext;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Тесты на контекст бинов
 */
class EasyContextTest {
  private static class Bean1 {
    @Override
    public String toString() {
      return "I'm bean 1";
    }
  }

  private static class Bean2 {
    @Override
    public String toString() {
      return "I'm bean 2";
    }
  }

  @Test
  public void testEasyContext() {
    EasyContext context = new EasyContext();

    context.addBean(new Bean1());
    context.addBean(new Bean2());

    assertEquals("I'm bean 1", context.getBean(Bean1.class).toString());
    assertEquals("I'm bean 1", context.getBean(Bean1.class).toString());
    assertEquals("I'm bean 2", context.getBean(Bean2.class).toString());
  }
}