# How to setup Appium on Mac

## Prerequisite

### Install homebrew (package manager for macOS is used to install software packages)

Link: https://brew.sh/

Command: `/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)`

Install node and npm (Appium dependencies)
===========================================
Commands to check if node and npm are installed:
node -v
npm -v
Command to install node: brew install node (This will install npm as well)
Command to check node installation path: where node

Install Appium server using NPM (Appium CLI)
==============================================
Command to install Appium: npm install -g Appium
Command to check Appium version: appium -v
Command to check Appium installation path: where appium

OR

Install Appium server using Appium Desktop client
=================================================
Download link: https://appium.io

Install Appium Inspector
=====================
-> Download and install from https://github.com/appium/appium-inspector/releases

Install JAVA JDK (not the JRE!)
===========================================================
- Command to check if JAVA is already installed: java -version
- JAVA JDK download link: https://www.oracle.com/technetwork/java/javase/downloads/index.html
Important note: Please use Java 8/11/15 for now. Don't use Java 16 or higher. The current Appium Java Client 7.6.0 is not compatible with Java 16+. You may use Java 16+ once Appium Java client 8.0.0 is released to the market.


Install Android Studio
=================================================================
- Android Sutdio download link: https://developer.android.com/studio


Set JAVA_HOME and ANDROID_HOME environment variables
=======================================================
Option1 (zprofile - MacOS Catalina default shell is zsh): 
--------------------------------------------------------
-> Navigate to home directory: cd ~/
-> Open zprofile file: open -e .zprofile
-> Create zprofile file: touch .zprofile
-> Add below entries:
export JAVA_HOME=$(/usr/libexec/java_home)
Important note: If above path doesn't work, try /Library/Java/JavaVirtualMachines/your_jdk_version/Contents/Home
Here, your_jdk_version can be jdk15.0.2.jdk for example.
export ANDROID_HOME=${HOME}/Library/Android/sdk
export PATH="${JAVA_HOME}/bin:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools:${PATH}"
-> source .zprofile

Option2 (zprofile and bashprofile):
----------------------------------
-> Navigate to home directory: cd ~/
-> Open bash profile file: open -e .bash_profile
-> Create bash profile: touch .bash_profile
-> Add below entries:
export JAVA_HOME=$(/usr/libexec/java_home)
Important note: If above path doesn't work, try /Library/Java/JavaVirtualMachines/your_jdk_version/Contents/Home
Here, your_jdk_version can be jdk15.0.2.jdk for example.
export ANDROID_HOME=${HOME}/Library/Android/sdk
export PATH="${JAVA_HOME}/bin:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools:${PATH}"
-> Open zprofile file: open -e .zprofile
-> Create zprofile file: touch .zprofile
-> add this line: source .bash_profile
echo $JAVA_HOME
echo $ANDROID_HOME
echo $PATH

Verify installation using appium-doctor
==========================================
- Command to install appium-doctor: npm install -g appium-doctor
- Command to get appium-doctor help: appium-doctor --help
- Command to check Android setup: appium-doctor --android 


Emulator Setup: Create AVD and start it
==========================================
Open Android Studio
Click Configure option
Click "AVD Manager" option
Click "Create Virtual Device" button
Select the phone model
Download the Image for desired OS version if not already downloaded
Start AVD
