package edu.gandhi.prajit;

import com.google.protobuf.util.JsonFormat;
import edu.gandhi.model.proto.OneOfAKind;
import edu.gandhi.model.proto.Person;
import edu.gandhi.model.proto.RainbowColor;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.UUID;

public class ProtocolBufferObjectCreation {
    static void main() {
        Person message = Person.newBuilder()
                .addUniqueIdentifier(UUID.randomUUID().toString()).addUniqueIdentifier(UUID.randomUUID().toString())
                .putColorMap(RainbowColor.RED.name(), RainbowColor.RED)
                .putColorMap(RainbowColor.GREEN.name(), RainbowColor.GREEN)
                .putColorMap(RainbowColor.BLUE.name(), RainbowColor.BLUE)
                .build();
        System.out.println(MessageFormat.format("Creating Protocol Buffer Object For The First Time:\n{0}", message));

        OneOfAKind oneOfAKindFirstMessage = OneOfAKind.newBuilder().setEmail("example@email.com").setFullName("Gandhi").build();
        System.out.println(MessageFormat.format("First Message:hasEmail:{0},hasFullName:{1}", oneOfAKindFirstMessage.hasEmail(), oneOfAKindFirstMessage.hasFullName()));
        OneOfAKind oneOfAKindSecondMessage = OneOfAKind.newBuilder(oneOfAKindFirstMessage).setEmail("outlier@email.com").build();
        System.out.println(MessageFormat.format("Second Message:hasEmail:{0},hasFullName:{1}", oneOfAKindSecondMessage.hasEmail(), oneOfAKindSecondMessage.hasFullName()));

        writeTo(message, "PersonOuterClass.Person.bin");
        Person deserialized = readFrom("PersonOuterClass.Person.bin");

        System.out.println("deserialized.getColorMapCount() == message.getColorMapCount():" +
                (deserialized.getColorMapCount() == message.getColorMapCount()));
        System.out.println("deserialized.getUniqueIdentifierCount() == message.getUniqueIdentifierCount():" +
                (deserialized.getUniqueIdentifierCount() == message.getUniqueIdentifierCount()));

        System.out.println("Converted To Json:" + toJson(message));
        System.out.println("Converted From Json:" + fromJson(toJson(message)));
    }

    @SneakyThrows
    private static void writeTo(Person person, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            person.writeTo(fileOutputStream);
            System.out.println(MessageFormat.format("PersonOuterClass.Person serialized to {0}", path));
        }
    }

    @SneakyThrows
    private static Person readFrom(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            return Person.parseFrom(fileInputStream);
        }
    }

    @SneakyThrows
    private static String toJson(Person person) {
        return JsonFormat.printer().omittingInsignificantWhitespace().print(person);
    }

    @SneakyThrows
    private static Person fromJson(String json) {
        final Person.Builder person = Person.newBuilder();
        JsonFormat.parser().merge(json, person);
        return person.build();
    }
}
