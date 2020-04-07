package task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobResult {

    private int total;

    private int successCount;

    private int processedCount;

    private int failCount;
}
