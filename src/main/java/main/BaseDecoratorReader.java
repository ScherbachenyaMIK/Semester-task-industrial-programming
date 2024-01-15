package main;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;

public class BaseDecoratorReader extends readerSource {
    protected readerSource reader;

    BaseDecoratorReader(readerSource reader_) {
        super(reader_.getFilename());
        reader = reader_;
    }

    @Override
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        return reader.ReadListOfMathExpressions();
    }
}

abstract class readerSource {
    abstract ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException;

    // String with name of file to read
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected String filename;

    readerSource (String filename_) {
        filename = filename_;
    }
}