package net.diamonddev.dialabs.cca;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface BoolComponent extends Component {
    boolean isComponentTrue();
    void setComponent(boolean bool);
}
