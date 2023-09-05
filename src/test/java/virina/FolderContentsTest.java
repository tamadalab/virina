package virina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class FolderContentsTest {
    @Test
    void main() {
        FolderContents testMain = new FolderContents();
        testMain.main("./testdata");
    }
}