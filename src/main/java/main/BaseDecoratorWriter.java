package main;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;

public class BaseDecoratorWriter extends writerSource {
    protected writerSource writer;

    BaseDecoratorWriter(writerSource writer_) {
        super(writer_.getFilename());
        writer = writer_;
    }

    @Override
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        writer.WriteListOfResultsOfMathExpressions(expressions, type);
    }
}

abstract class writerSource {
    public abstract void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException;

    // String with name of file to write
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected String filename;

    // String with source name of file to write
    @Getter(AccessLevel.PROTECTED)
    protected String source_filename;

    // Counter of opened temps or just position of decorator in queue
    @Setter(AccessLevel.PROTECTED)
    int temps_counter = 0;

    writerSource (String filename_) {
        filename = filename_;
        source_filename = filename_;
    }
}