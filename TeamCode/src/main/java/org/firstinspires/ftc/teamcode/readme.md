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

# DarkHorse V0.1.4.4

The overall version naming scheme is as follows:

{tweak}.{major}.{minor}.{revision}

The tweak is responsible for keeping ***multiple versions of the same file*** in check, for example, 
the Mecanum Drive that inherits from the abstract Drivetrain class is considered tweak 0, and the
Mecanum Drive that doesn't inherit from the abstract Drivetrain is considered tweak 1. 

The major version holds ***breaking changes***. These are changes that required rewriting more than
the target file, for example, the mecanum drive's autonomous commands changing from the specific
commands (Major 0) to the more general universal mode it is now (Major 1)

The minor version holds ***New features***. This would include a 3-axis arm (Minor 2) from a
cascade arm (Minor 1). The functional change did not break any classes, but did require a rewrite 
of the main class Deimos.

the revision hold ***bug fixes***. This includes the daily changes made, and so this version 
increments once per day since the last major or minor version change. These changes do not require any
rewriting of any other file than the one the fix is in. 

The overall version of the project is represented as the highest version component of any file. If the
drivetrain was at version 1.2.2.1 and the arm at version 0.4.5.1, the overall TeamCode version would 
be 1.4.5.1.

## But this is so complex, why can't it all be in one file?

It could. It absolutely could. But then all of a sudden complex systems with autonomous and driver
commands, dashboard syncs and updates all in the same file, become 600+ lines of code, and very hard
to organize. This way, one only needs to know the concepts behind the Core classes, and they don't 
have to worry about the exact nature of the file. For example, the CoreMessenger class seems very 
complex when you try to understand the logic of it. But the concept behind it is the same as an 
extended version of telemetry. By itself, it would be cut as too much extra code, but together with
sensors and subsystems, it finds its role in very clearly.