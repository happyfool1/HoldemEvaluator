


This set of applications is for evaluating Texas Hold'em no-limit 6-max poker. It is part of a research project searching for simple methods to increase win rates. I am not a purist. I do not care if the method is mathematically 100% correct, GTO, AI, exploitation, or whatever. The methods must increase win rates and be simple enough for an average person to apply at the table or online.

I have written a lot of code during this research. Anyone who wants is free to use all or part of the code. I do hope that I can get others to participate in this project by suggesting new ideas or by actually developing new code.

The code is in four broad categories:

1. **Hold’em Evaluation:** This code does a Monte Carlo-type simulation that evaluates the results of play on the Flop, Turn, River, and Showdown. It creates indexes based on hand structure and board structure, runs millions of simulated hands, and categorizes the results.
1. **Hold’em Hand History:** Analyzing many millions of actual hands in a unique way, not like the very successful Hold’em Manager using a relational database but with a collection of objects that allow for a new unique type of analysis. This includes things like the actual results of bet size on opponent responses.
1. **Hold’em Simulator:** Using the results from the other two projects, the simulator uses data from the Hand History analysis to simulate how an opponent will respond to situations at the table. The simulation then searches for exploits of the opponent's behavior.
1. **Hold’em Coach:** An application that uses results from the other three to play against the simulator with coaching prompts and exercises to learn how to increase your win rate. The ultimate goal of all this.

All four projects are being done in parallel with code being shared between them. All are at about the same level of completion. They are all in test and debug, past the prototype stage but far from complete. These are research projects that are interrelated. I will be placing the code for all four on GitHub within the next few months.

This is Hold’em Evaluator. 

Please bear with me for some very oversimplified explanations. In order to be useful, the analysis must not be arbitrary and must have very precise definitions. Terms like a wet Flop are both very arbitrary and without a precise definition. I can find no definition for a wet Flop anywhere, but it is used everywhere. Deal 100 Flops analyzed by 100 experts, and you will have somewhere between 100 and 10,000 definitions. I would go all-in if you said 100. Never happen. The basic information about a Flop hand is very simple. Two hole cards are combined with 3 board cards to make draws and made hands. The 2 hole cards have exactly 169 possible combinations. The Flop has exactly 1755 combinations. Combine the cards to make a draw or a made hand, and the number is unmanageable. What if we can find a way to simplify?

Let’s start simplifying. Any Flop consists of simple properties: Value, paired, connectedness, and suitedness. First, sort the board by value and by suit. Pairs are when the value gap is 0, a gap of 1 indicates connectedness. A gap of 0 between suit value indicates suitedness. The basic building blocks of both draws and made hands. Not arbitrary and relatively simple. If the Flop is first sorted in order, and each card is assigned as High (A-T), Medium (9-6), and Low (5-2), then any Flop will have exactly 10 possible results: HHH through LLL. An index value of 0 - 9. Suppose we then simulate play through Showdown and develop a matrix of final results. Would it be useful to know what frequency of time a HML Flop will produce a Straight or what the most frequent winning hand will be?

How about if we develop an index based on value, paired, suitedness, and connectedness? You can easily do the calculation in your head. The resulting index being exactly 21. You can see the basic idea, a relatively quick and simple way to calculate an index with predictive value.

Game theory Optimal, Exploitation, and the application of artificial intelligence and rule-based systems popular in many books. All of them are simply far too complex for all but a gifted few. I have studied them for many years, have slowly become better at the game, but have been unable to apply the theory quickly and accurately enough to beat the best players. I am a retired programmer, have a sharp and analytical mind, and have worked hard to master the game. Dumb or lazy are not my problems. What I am searching for is shortcuts that will work at the table.

To help us in our mission, we've been using ChatGPT as an assistant, providing ideas for flop characterization and guiding hand play based on analysis.

Mathematics can be very misleading. Ask a mathematician what is the probability that our opponent has a flush draw when the Flop has two cards of the same suit, and a number will be forthcoming. Precise and completely misleading because the answer depends entirely on what two cards he is holding. Is he a TAG playing UTG or a LAG on the Button? How about who will win the hand? Should we ask the mathematician? GTO? AI? Which will have the best answer? My point exactly, we really do not know. Simulation does give me a pretty good answer though. More on that later.

**GUIAnalyzeIndexArrays –** This cutting-edge tool simulates and analyzes thousands of poker hands, evaluating various flop types based on the hands' results. It's our most critical feature in this update and will greatly help to identify key flop characteristics that can influence your game play strategy.

**GUI –** A dynamic tool that enables you to choose hole cards and board cards and then runs an in-depth analysis of draws, made hands, and showdown potential.

**GUIEditRanges –** A nifty tool that allows you to adjust hand ranges for preflop play, which is essential in creating a strategic approach.

Please note that these tools are currently being perfected, and we appreciate your patience and feedback during this period.

**GUIAnalyzeIndexArrays** is central to our mission of discovering valuable flop board characteristics to optimize your hand play strategy. With it, you can now run thousands of random hands, studying each player's hands at every stage (Flop, Turn, River, and Showdown). Once all hands have been played, the data is stored and analyzed to help fine-tune your game strategy. Hand history files have gone a long way in supplying answers. See the undeniable success of Holdem Manager. But the HM architects, while doing a superb job, did it within the confines of a relational database. Some questions are too difficult for relational alone.

I am looking at many millions of hand history files in a different way. I want to know the answers to questions like "If I raise 44% of the pot vs. 55% of the pot, how does that change how often my opponent will fold?" "If I want him to fold, what is the best percentage to raise?" "If I want him to call?" "If I want him to raise?" I was very surprised at the initial results that I got.

Enough for now on the general idea of what I am trying to research. The goal is to find simple ways that certain guidelines, that are accurate and not arbitrary, can be determined away from the table but used reliably at the table. I just want to win more.

In our upcoming update, expect to see new algorithms and features that suggest how to play a hand based on various scenarios: missing the flop, connecting with draws, or making hands. This guidance, although somewhat subjective, will be more grounded and precise as we continue refining our systems.

The **GUI** tool will also boost your gaming experience. It's a fantastic experimental platform where you can select or randomize hole and board cards for in-depth analysis. Whether you want to play and analyze one hand or several, **GUI** provides the flexibility you need.

**GUIEditRanges** offers another level of strategy refinement. Simulating 6-player preflop play, it uses hand ranges to determine play strategies based on position and betting sequence. You can adjust these ranges to suit your style or strategy, offering you greater control over your game.

Additionally, keep an eye out for our upcoming project, **PeakHoldemHandHistory**, soon to be launched on GitHub. It offers detailed analysis of over 10 million PokerStars 6-max no-limit $1/$ hand history files, delivering insights and strategic considerations that no other application currently provides. The information derived from this tool will feed into your game play, guiding your decisions based on real-world data.

Join us on this journey of poker mastery as we strive to provide you with the best analytical tools and strategic advice. Be prepared for some surprises along the way as we discover new insights into the game's dynamics. Stay tuned for our upcoming updates, and in the meantime, happy gaming!

We are sharing our advanced poker analysis project code on GitHub for everyone to freely utilize. However, if you decide to use it in your own project, kindly remember to give due credit.

This project is all about in-depth analysis of thousands, even millions, of simulated poker hands. By employing a Monte Carlo methodology, we can dive into the intricacies of a 6-max no-limit Hold'em game. Our main focus is to devise ways to categorize a flop, for example, using the High-Medium-Low (HML) method, resulting in only 10 flop types. For every hand run, we collate detailed data on draws, made hands, and other aspects for post-run analysis, covering every stage, including the showdown. For the Turn, 15 Flop types and for the River, 21 Flop types.

For completeness, we also have analysis of Wet / Dry / Neutral. Our algorithm is complex and we think pretty darn accurate.

There are 1755 unique flops. We developed code to generate these flops and the converse to take any flop and determine which of the 1755 flops it corresponds to. There are: 13 sets, 156 pairs suited, 156 pairs offset, 286 suited, 286 off suit, 286 2 suited low, 286 2 suited high, 286 2 suited high, and 286 suited high and low. See Class Flop1755Methods for more information. We use 9 groupings as a flop index.

Another index is constructed using Suitedness, Connectivity, Big cards, and Paired flop characteristics. From this, we develop 16 flop indexes. Looks very promising. I'll be experimenting with variations of this. Another algorithm that can be applied relatively easily in a real game.

Please note that this project is constantly evolving as we persistently experiment to find the most informative ways to characterize a flop, thus guiding you to play your hand most effectively. Whether it's a scenario that leads to a high percentage of made hands, strong draws, or instances where the made hand becomes the winning hand at showdown, we're uncovering it all. Refer to the **GUIAnalyzeMany Class** for more insights.

A companion project called **PeakHoldemHandHistory** is also in the pipeline, where we delve into over ten million PokerStars Hand History files, investigating factors such as the influence of bet size on a player's decision to call or fold, amongst many other aspects. 

Finally, about me - I'm an 80-year-old coder who wrote my first program in 1965, in machine language. I spent 30 years at IBM, where I developed three compilers and contributed to the Fortran optimizer. Most of my career was in manufacturing engineering, testing equipment, procedures, automation, and data collection. I've been coding ever since to keep my mind active. Always learning, always evolving! I go way back. I was friends with the designer of the IBM PC. No one knows his name. Now retired, I continue to code to keep my mind active. I'm an old-timer learning new tricks.



