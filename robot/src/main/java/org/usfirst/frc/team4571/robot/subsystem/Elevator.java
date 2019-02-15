package org.usfirst.frc.team4571.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4571.robot.Constants;
import org.usfirst.frc.team4571.robot.command.teleop.TeleOpElevator;

public class Elevator extends Subsystem {
    private static Elevator instance;
    private TalonSRX baseMotor;
    private TalonSRX topMotor;
    private DigitalInput limitSwitch;

    private Elevator() {
        baseMotor = new TalonSRX(Constants.Elevator.TOP_MOTOR);
        topMotor = new TalonSRX(Constants.Elevator.BASE_MOTOR);
        baseMotor.setNeutralMode(NeutralMode.Brake);
        topMotor.setNeutralMode(NeutralMode.Brake);
        limitSwitch = new DigitalInput(Constants.Elevator.LIMIT_SWITCH);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TeleOpElevator());
    }

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    public void setBaseMotor(double value) {
        baseMotor.set(ControlMode.PercentOutput, value);
    }

    public void setTopMotor(double value) {
        topMotor.set(ControlMode.PercentOutput, value);
    }

    public void stopBaseMotor() {
        baseMotor.set(ControlMode.PercentOutput, 0);
    }

    public void stopTopMotor() {
        topMotor.set(ControlMode.PercentOutput, 0);
    }

    public boolean isLimitSwitchPressed() {
        return limitSwitch.get();
    }
}
