package View;

import Model.Vacation;

public interface Item {
    void defineContent(Vacation item);
    String getType();
}
