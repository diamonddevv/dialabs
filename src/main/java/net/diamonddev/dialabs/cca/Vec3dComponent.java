package net.diamonddev.dialabs.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.util.math.Vec3d;

public interface Vec3dComponent extends Component {
    Vec3d get();
    void set(Vec3d vec);
}
