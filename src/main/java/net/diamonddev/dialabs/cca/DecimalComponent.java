package net.diamonddev.dialabs.cca;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface DecimalComponent extends Component {

    double getValue();
    void setValue(double d);
}
