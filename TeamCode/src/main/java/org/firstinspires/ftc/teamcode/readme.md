# Big Design Choices

## CoreOpMode

This new class is created to continuously update all the sensors and subsystems regardless of if we 
are in a sleep loop or not. This also allows for Subsystems to update even inside of sleep calls.

## CoreSubsystem and CoreSensor

These are responsible for containing abstract calls for the sensors. Further stateMachine info and 
actual method calls should be handled in their own child classes. This allows for the drivetrain to 
continuously update even when there is a loop being handled for a separate system. 

## Drivetrain Abstract Class

Even though the only drivetrain is a MecanumDrive, the abstract drivetrain class is to simplify the 
massive MecanumDrive file. This cannot be accomplished with a interface for the reason of function 
bodies. Potential uses where interfaces could be used instead of abstract classes, but my personal 
preference is to use abstract classes for two reasons:

1. It does not require deciphering what should be used of an interface vs an abstract class, as 
from a programming perspective it makes no difference.
2. Allows for further extensibility instead of forcing certain methods and also allows for common 
methodology. 

This also allows for seamless RoadRunner integration for autonomous commands without severely 
bloating the separate drive implementations. 