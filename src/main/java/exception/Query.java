package exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Query {

    private String param;

    public void validate() throws ParamException {
        if (param == null) {
            throw new ParamException("param should not be null");
        }
    }
}
