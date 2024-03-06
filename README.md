 We've made some game-changing enhancements in our recent update. We've introduced three brand new interactive tools designed to improve your poker game:

GUIAnalyzeIndexArrays – This cutting-edge tool simulates and analyses thousands of poker hands, evaluating various flop types based on the hands' results. It's our most critical feature in this update and will greatly help to identify key flop characteristics that can influence your game play strategy.

GUI – A dynamic tool that enables you to choose hole cards and board cards and then runs an in-depth analysis of draws, made hands, and showdown potential.

GUIEditRanges – A nifty tool that allows you to adjust hand ranges for preflop play, which is essential in creating a strategic approach.

Please note that these tools are currently being perfected, and we appreciate your patience and feedback during this period.

GUIAnalyzeIndexArrays is central to our mission of discovering valuable flop board characteristics to optimize your hand play strategy. With it, you can now run thousands of random hands, studying each player's hands at every stage (Flop, Turn, River, and Showdown). Once all hands have been played, the data is stored and analyzed to help fine-tune your game strategy.

We've also debunked the conventional wisdom of characterizing flops as wet, dry, or neutral – a flawed and limited approach. Instead, we're exploring alternative, more accurate ways to characterize a flop, such as using High, Medium, Low (HML) rating systems, or evaluating connectivity, suitedness, card value, and pairs. Our goal is to offer better, evidence-based strategic options for players.

Our code is designed to be flexible and user-friendly, making the addition of new flop characterization algorithms quick and straightforward.

In our upcoming update, expect to see new algorithms and features that suggest how to play a hand based on various scenarios: missing the flop, connecting with draws, or making hands. This guidance, although somewhat subjective, will be more grounded and precise as we continue refining our systems.

To help us in our mission, we've been using ChatGPT as an assistant, providing ideas for flop characterization and guiding hand play based on analysis.

The GUI tool will also boost your gaming experience. It's a fantastic experimental platform where you can select or randomize hole and board cards for in-depth analysis. Whether you want to play and analyze one hand or several, GUI provides the flexibility you need.

GUIEditRanges offers another level of strategy refinement. Simulating 6-player preflop play, it uses hand ranges to determine play strategies based on position and betting sequence. You can adjust these ranges to suit your style or strategy, offering you greater control over your game.

Additionally, keep an eye out for our upcoming project, PeakHoldemHandHistory, soon to be launched on GitHub. It offers detailed analysis of over 10 million PokerStars 6-max no limit $1/$ hand history files, delivering insights and strategic considerations that no other application currently provides. The information derived from this tool will feed into your game play, guiding your decisions based on real-world data.

Join us on this journey of poker mastery as we strive to provide you with the best analytical tools and strategic advice. Be prepared for some surprises along the way as we discover new insights into the game's dynamics. Stay tuned for our upcoming updates, and in the meantime, happy gaming!


We are sharing our advanced poker analysis project code on GitHub for everyone to freely utilize. However, if you decide to use it in your own project, kindly remember to give due credit.

This project is all about in-depth analysis of thousands, even millions, of simulated poker hands. By employing a Monte Carlo methodology, we can dive into the intricacies of a 6-max no-limit Hold'em game. Our main focus is to devise ways to categorize a flop, for example, using the High-Medium-Low (HML) method, resulting in only 10 flop types. For every hand run, we collate detailed data on draws, made hands, and other aspects for post-run analysis, covering every stage including the showdown. For the Turn 15 Flop types and for the River 21 Flop types.

For completeness, we also have analysis of Wet / Dry / Neutral. Our algorithm is complex and we think pretty darn accurate.

There are 1755 unique flops. We developed code to generate these flops and the converse to take any flop and determine which of the 1755 flops it corresponds to. There are:
13 	sets, 156 pairs suited,	156 pairs offsuit, 286 suited, 286 offsuit,	286 2 suited low, 286 2 suited high, 
286 2 suited high,and 286 suited high and low. See the Class Flop1755Methods for more information.
We use 9 groupings as a flop index.

Another index is constructed using 	Suitedness, Connectivity, Big cards, and Paired flop characteristics. From this we develop 16 flop indexes. Looks very promising. I'll be experimenting with variations of this. Another algorithm that can be applied relatively easy in a real game.

Please note that this project is constantly evolving as we persistently experiment to find the most informative ways to characterize a flop, thus guiding you to play your hand most effectively. Whether it's a scenario that leads to a high percentage of made hands, strong draws, or instances where the made hand becomes the winning hand at showdown, we're uncovering it all. Refer to the GUIAnalyzeMany Class for more insights.

A companion project called PeakHoldemHandHistory is also in the pipeline, where we delve into over ten million PokerStars Hand History files, investigating factors such as the influence of bet size on a player's decision to call or fold, amongst many other aspects. Expect its release in a couple of months.

Moreover, the project has several supporting classes that analyze the board and hole cards to determine draws and made hands. The GUI Class is particularly noteworthy.

A bit of history about this work – its roots lie in the PeakHoldem 6-max Texas Hold'em product I started years ago. Due to health issues, the project was left incomplete, although it was nearly finished. Now, with improved health, I plan to complete this and the Hand History analysis project. However, I don't plan to commercialize them.

With PeakHoldemEvaluator and PeakHoldemHandHistory, I aim to provide unique perspectives on poker hand analysis. A majority of the classes coded for this project are tested and will soon be available publicly.

The code contains multiple useful classes that could support your projects, including deck and card classes. For instance, if you need to deal cards randomly, these classes can be of assistance.

We're also introducing innovative classes with preflop ranges and editors, like HandRange, HandRangeMultiple, EditRange, and GUIEditRange, among others. The HandHistory class, which creates PokerStars-like Hand History files, could also prove to be beneficial.

PeakHoldemEvaluator, a key part of this project, conducts detailed assessments of millions of hands. It characterizes flops either as HML or as one of the 1755 possible flops, and reviews each flop's performance at showdown.

Another intriguing venture, PeakHoldemSimulator, replicates a 6-max no-limit Texas Hold'em game, using this Class to evaluate the Hero's hand in the game and gather extensive data.

Finally, about me - I'm an 80-year-old coder who wrote my first program in 1965, in machine language. I spent 30 years at IBM, where I developed three compilers and contributed to the Fortran optimizer. Most of my career was in manufacturing engineering, test equipment, procedures, automation, and data collection.  I've been coding ever since to keep my mind active. Always learning, always evolving! I go way back. I was friends with the designer of the IBM PC. No one knows his name. Now retired, I continue to code to keep my mind active. I'm an old-timer learning new tricks.


