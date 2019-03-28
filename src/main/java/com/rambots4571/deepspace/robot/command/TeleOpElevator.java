package com.rambots4571.deepspace.robot.command;

import com.rambots4571.deepspace.robot.subsystem.Elevator;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

import static com.rambots4571.deepspace.robot.Robot.gamepad;

public class TeleOpElevator extends Command {
    private Elevator elevator = Elevator.getInstance();

    public TeleOpElevator() {
        requires(elevator);
    }

    @Override
    protected void initialize() {
        elevator.teleOpInit();
    }

    @Override
    protected void execute() {
        gamepad.getRightBumper().whenReleased(
                new InstantCommand(elevator::togglePositionMode));
        // set position
        if (gamepad.getY().get())
            elevator.setPosition(Elevator.Height.Top);
        else if (gamepad.getB().get())
            elevator.setPosition(Elevator.Height.Middle);
        else if (gamepad.getA().get())
            elevator.setPosition(Elevator.Height.Bottom);
        else if (gamepad.getLeftBumper().get()) elevator.setPosition(0);
        else elevator.setBaseMotor(gamepad.getLeftYAxis());
        // small elevator manual control
        if (gamepad.getPOV() == 0) elevator.setTopMotor(1);
        else if (gamepad.getPOV() == 180) elevator.setTopMotor(-1);
        else elevator.setTopMotor(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        elevator.stopBaseMotor();
        elevator.stopTopMotor();
    }
}