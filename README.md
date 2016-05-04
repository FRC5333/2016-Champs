# 2016-Champs
Our 2016 Code Rewrite for use at the World Championships in St Louis

A lot of the code here reflects that of our [Stronghold Repo](https://github.com/FRC5333/2016-Stronghold), with some differences. If you want a long, thoughtout readme, I suggest viewing that one.

In this repo, the notable differences are an increase in simplicity, with many superfluous pieces taken out in order to satisfy a neater, cleaner and easier to understand codebase. 

Motion Profiling was removed as it was deemed uncessary in Autonomous as it has a high probability to conflict with the crossings of other robots on the same alliance. 

While vision auto-alignment is present in the repo, it wasn't used during competition. We found during testing that our driver was faster to align accurately than a well-tuned PID loop (humans tend to be really good at getting the angle "good enough" instead of trying for a 0.00000000000000 asimuth, and guessing the correct power to apply to the throttle given their experience with the robot. Our driver is capable of aligning a shot from the outer works in about 1.5 seconds, with a single regional's worth of training, while it takes a PID loop a couple of seconds to settle down)

Our vision code has been completely rewritten to support a faster 30fps framerate (the max of the camera, with a theoretical 60+ fps with our current processor) and to acquire targets more accurately. All vision targets are relayed to the Driver Station WebUI with an angle in degrees annotated above the visual representation of the target. The WebUI refreshes at 10fps by default, but is capable of 30fps with a simple configuration file tweak (5333-WebUI.conf)
