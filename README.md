<img style="margin: auto; display: block;" src="LOGO.png"  alt=""/>

<br>
<b>NoEncryptionX</b> is a Minecraft server plugin originally developed by <a href="https://github.com/Doclic/" target="_blank">Doclic</a> and <a href="https://github.com/V1nc3ntWasTaken/" target="_blank">V1nc3ntWasTaken</a>,
now updated and maintained for newer versions by <a href="https://github.com/ineanto/" target="_blank">Ineanto</a>
(me!).

NoEncryptionX is a Minecraft plugin intended to <b>combat against</b> the new chat reporting system implemented in 1.19
by safely removing encryption.

## What versions does NoEncryptionX support?

NoEncryptionX supports versions <code>1.19</code> through <code>1.21.11</code>.

## How does NoEncryptionX work?

<img src="https://user-images.githubusercontent.com/79623093/223909733-979c8cdf-faa5-499d-afe3-4bd1fef8a7ac.png"  alt=""/>
NoEncryptionX works by <b>stripping</b> messages of their signatures,
leaving <b>no way</b> for a client to <b>validate</b> that a chat message or command came from a certain player,
thus making your messages un-reportable. 
NoEncryptionX modifies outbound packets specifically to allow other plugins, such as chat managers, 
to properly modify chat contents before reaching NoEncryptionX.

## Where are the config files?

NoEncryptionX stores its files under <code>plugins/NoEncryptionX/</code>.

## Is there any way to remove the gray bar and indent?

<img style="float: left; padding-right: 10px" height=120px src="https://user-images.githubusercontent.com/79623093/224067339-addb6165-8d78-460f-8e8c-fbeeaebb698f.png" alt=""/>
Currently, there is <b>no way</b> to remove the gray bar and indent without the use of texture packs.
This is because it is in the clientside code to include the gray bar for unsigned messages,
and always include the indentation.
There is an available texture pack that solves this issue,
which can be found <a href="https://modrinth.com/resourcepack/hide-chat-toasts-and-chat-bars/version/2.0">here</a>.

<br>
<br>

## Isn't injecting into a player's connection a security vulnerability?

The NoEncryptionX packet handler is designed to intercept only certain packet types,
such as <b>ClientboundPlayerChatPacket</b>,
<b>ClientboundPlayerChatHeaderPacket</b>, and <b>ClientboundServerDataPacket</b>
(to block the annoying popup).
<br>
When one of these packets is detected, its contents are stored
and a brand-new packet is created in place of it,
reusing the contents that were stored from the original packet,
and dropping certain contents such as the signature and the salt.

## How can I contribute to NoEncryptionX?

You can make contributions to the NoEncryptionX project through the use
of <a href="https://github.com/Inenato/NoEncryptionX/pulls">Pull Requests</a>. When making a contribution, make sure
that
you review all the checks in the PR.

<details>
<summary>Contributing and Building</summary>

<details>
<summary>Requirements</summary>

- Java JDK
- Gradle

</details>

<details>
<summary>Java Installation</summary>

To build NoEncryptionX and use Gradle, you need an installation of the Java JDK.
It is recommended to use Java 17 as this is the version that NoEncryptionX is developed with.

<details>
<summary>Cloning and Building NoEncryptionX</summary>

To locally clone the NoEncryptionX repository to your local file system, you can use Git to clone the repository
with <code>git clone https://github.com/ineanto/NoEncryptionX</code>

After cloning NoEncryptionX, you can build the plugin through Maven using the following commands:
</details>

</details>

<details>
<summary>Building NoEncryptionX</summary>

1. Open the cloned NoEncryptionX folder in a terminal window.
2. After the above command completes, run the following command to build the JAR files:
   <code>./gradlew build</code>
3. After the above command completes, a new <code>target</code> folder will be created.
4. Inside the <code>build/libs</code> folder will be multiple JAR files.
   The JAR file that should be used is <code>
   NoEncryptionX-VERSION.jar</code>.
   Do not use JAR files starting/ending with <code>original-</code>, <code>
   -remapped.jar</code>, or <code>-remapped-obf.jar</code>.

</details>

</details>