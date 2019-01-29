package org.usfirst.frc.team4571.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4571.robot.commands.autonomous.FollowPath;
import org.usfirst.frc.team4571.robot.commands.autonomous.TurnCommand;
import org.usfirst.frc.team4571.robot.commands.teleop.TeleOpDrive;
import org.usfirst.frc.team4571.robot.subsystems.DriveSystem;

public final class Robot extends TimedRobot {
    public static final DriveStick leftStick = new DriveStick(
            Constants.Controllers.LEFT_STICK);
    public static final DriveStick rightStick = new DriveStick(
            Constants.Controllers.RIGHT_STICK);
    private final SendableChooser<Command> autoChooser
            = new SendableChooser<>();
    private Command autoCommand;

    public final static DriveSystem DRIVE_SYSTEM = new DriveSystem();

    Robot() {
        super(Constants.period);
    }

    public void robotInit() {
        autoChooser.addOption("Turn 90 Degrees", new TurnCommand(90.0D));
        autoChooser.addOption("Run test path", new FollowPath("testpath"));
        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    public void disabledInit() {
        Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        autoCommand = autoChooser.getSelected();
        if (autoCommand != null) {
            autoCommand.start();
        }
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }
        Scheduler.getInstance().add(TeleOpDrive.getInstance());
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}