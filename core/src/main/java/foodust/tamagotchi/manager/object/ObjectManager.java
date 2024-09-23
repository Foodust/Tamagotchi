package foodust.tamagotchi.manager.object;

import foodust.tamagotchi.object.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObjectManager {
    private List<BaseObject> objects = new ArrayList<>();
    private static ObjectManager instance;

    public static ObjectManager getInstance() {
        if (instance == null) {
            instance = new ObjectManager();
        }
        return instance;
    }
}
