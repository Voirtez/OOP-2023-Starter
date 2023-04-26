package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;

public class DANI extends PApplet
{

	public void settings()
	{
		size(1000, 1000);
		//fullScreen(SPAN);
	}

    String[] sonnet;
	String[] words;
	String[] newSonnet;
	ArrayList<Word> word = new ArrayList<Word>();

    public String[] writeSonnet()
    {
		String[] newSonnet = new String[14];

		for(int i = 0; i < newSonnet.length; i++)
		{
			String line = "";

			String randWord = word.get((int)random(word.size())).getWord();
			line += randWord + " ";

			if(findWords(randWord).getFollows().size() > 0)
			{
				String randFollow = findWords(randWord).getFollows().get((int)random(findWords(randWord).getFollows().size())).getWord();
				line += randFollow + " ";
			}
			else
			{
				newSonnet[i] = line;
				continue;
			}

			newSonnet[i] = line;

		}

		return newSonnet;

    }

	public void setup()
	{
		colorMode(HSB);
		loadFile();
		printModel();

	}

	public void keyPressed()
	{
		if(key == ' ')
		{
			newSonnet = writeSonnet();
			for(int i = 0; i < newSonnet.length; i++)
			{
				System.out.println(newSonnet[i]);
			}
		}
	}

	public void loadFile()
	{
		sonnet = loadStrings("shakespere.txt");

		words = join(sonnet, " ").split(" ");
		
		for(int i = 0; i < words.length; i++)
		{
			words[i] = words[i].replaceAll("[^a-zA-Z ]", "");
		}

		for(int i = 0; i < words.length; i++)
		{
			words[i] = words[i].toLowerCase();
		}
		

		for(int i = 0; i < words.length - 1; i++)
		{
			if(findWords(words[i]) == null)
			{
				Word w = new Word(words[i], new ArrayList<Follow>());

				w.getFollows().add(new Follow(words[i + 1], 1));
				word.add(w);
			}
			else
			{
				for(Word w: word)
				{
					if(w.getWord().equals(words[i]))
					{
						if(!w.findFollow(words[i + 1]))
						{
							w.getFollows().add(new Follow(words[i + 1], 1));
						}
						else
						{
							for(Follow f: w.getFollows())
							{
								if(f.getWord().equals(words[i + 1]))
								{
									f.setCount(f.getCount() + 1);
								}
							}
						}
					}
				}
			}
		}
	}


	public Word findWords(String str)
	{
		for(Word w: word)
		{
			if(w.getWord().equals(str))
			{
				return w;
			}

		}

		return null;
	}


	public void printModel()
	{
		System.out.println("Model:");
		for(Word w: word)
		{
			System.out.println(w);
		}
	}

	float off = 0;


	public void draw() 
    {
		background(0);
		fill(255);
		noStroke();
		textSize(20);
        textAlign(CENTER, CENTER);

		if(newSonnet != null)
		{
			for(int i = 0; i < newSonnet.length; i++)
			{
				text(newSonnet[i], width / 2, height / 2 + (i * 30));
			}
		}

	}
}
