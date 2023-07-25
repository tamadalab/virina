package virina;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

// import java.nio.file.Path;

// import static org.junit.jupiter.api.Assertions.*;

class FolderContentsTest {

    @Test
    void main() {
        FolderContents testMain = new FolderContents();
        testMain.main("../testdata");
    }
    @Test
    void fileFinder() {
        FolderContents testFileFinder = new FolderContents();
        testFileFinder.fileFinder(Path.of("./app/src/").toFile());
    }

    @Test
    void api() {
        FolderContents testApi = new FolderContents();
        testApi.api(Path.of("./AppTest.java").toFile());
    }
}