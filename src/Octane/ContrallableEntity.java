package Octane;

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
