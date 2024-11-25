package Octane;

import TheProjekt.Camera;

public abstract class ContrallableEntity extends MovableEntity {
    private final MovementController controller;


    public ContrallableEntity(MovementController controller) {
        this.controller = controller;

    }

    public void moveWithController() {
        if (controller.isMoving()) {
            move(controller.getDirection());
        }
    }
}
