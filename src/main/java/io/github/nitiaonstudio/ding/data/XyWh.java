package io.github.nitiaonstudio.ding.data;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(staticName = "xywh")
public class XyWh {
    public int x,y,w,h;
}
