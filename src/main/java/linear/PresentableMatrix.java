package linear;

import java.nio.file.Path;

public interface PresentableMatrix extends Matrix {
    void saveToFile(Path file);
}
