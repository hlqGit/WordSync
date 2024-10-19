# WordSync
WordSync is a program I have created based off of a fun project I worked on a while ago. My goal was to create a program that could type the lyrics to "*Want You Gone*" by Jonathan Coulton, just as they appeared in the game Portal 2. Branching off of that, is this program! I aimed to make it easy to use, with no programming experience required.
## How it works
The program reads my new file type I created for this, the JWC file (Java WordSync). From that, it is able to type them out using the user-provided speeds and rests.
## Installation and running:
To install, download the code as a zip and extract it. Open the folder, and replace `audio.wav` with your own `.wav` file (must be named `audio.wav`. Optionally, change the code if you want it to be named something else). You can also keep it the same to test the program. Then, edit the `toPrint.jwc` as needed. Finally, open the terminal and navigate to that folder. Run the command: `java -jar WordSync.jar`, and it will run!
# How to write JWC files:
There are two main parts of each line in a JWC file. The words that will be typed out by the program, and the data the program uses to know when and how to print it. Here is an example line:
`Now little Caroline^^ is in here too \\ 4.0, [65 150]`
In order for this to be syntacticly correct, there must be two backslashes that indicate the seperation between the words and data portions of the line. **They must have a space before and after them.** Also, between each data provided there **MUST** be a comma followed by a space.
### Syntax
The very first line in any JWC file will always be the tempo. It is declared as followed: 

`$TEMPO=value`
`value`, is any number greater than 0. This should be the tempo of your song. You may have to experiment with it to get it completly accurate.

The syntax for every line will always be as follows.
`(What you want to be printed) \\ data1, data2, data3`
**data1**: Will always be rests
**data2**: Will always be line speeds
**data3**: Will always be terminal clearing
*This is subject to change as more features are added in the future.*

Each line **MUST** contain some value to print, or you will get unexpected results. If you want nothing to print out visibly, just use a space.
### Rests
The first piece of data to follow the backslashes are the rests. Those determine how many beats (provided the tempo of the song at the top of the JWC file).

*line 1* `Now little Caroline^^ is in here too \\ 4.0, [65 150]`
*line 2* `One day they woke me up \\ 8.5, [65]`

As soon as line one begins to type, line 2 is already counting 8.5 beats until it plays. If line1 was the actual first line in the file, there would be 4 beats before it would be typed out.
### Line speeds
The second piece of data is the line speeds. The correct syntax is as follows: 
`[speed1, speed2, speed3, etc.]`

Each "speed" is the delay between each character being typed in milliseconds. The smaller the number, the faster the text will be typed. In order for the program to know when to switch the speeds, you must add an indicator to the lyrics. You can do that by simply putting `^^` whenever you want the speed to change. The indicator ***will not*** be printed out by the program. Example provided:

`Now little Caroline^^ is in here too \\ 4.0, [65 150]`

"Now little Caroline" uses `speed1` to print. (65ms delay between each character.)
" is in here too" uses `speed2` to print. (150ms delay between each character.)
This can be done as many times as you want, even in the middle of a word.

**The speed must at least be greater than or equal to one.** Using 0 as the speed will cause the line to never print, as well as any after that. 
### Terminal clearing
This one is very simple. If you provide the word `clear` in the `data3` position, the terminal screen will be cleared, leaving the screen completly empty before the next lines are typed out. Example provided: 

` \\ 5.0, [65], clear`

Since each line **must** contain some value to print, this example prints a space and then clears the screen after 5 rests. The speed is completly negligible in this example, it wouldn't change anything.