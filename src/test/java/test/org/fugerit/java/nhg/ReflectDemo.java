package test.org.fugerit.java.nhg;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class ReflectDemo {

    @NonNull
    private String field1;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    @Getter @Setter
    private Integer field2;

}
