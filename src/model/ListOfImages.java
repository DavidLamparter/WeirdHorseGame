package model;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ListOfImages {
	private class ImageAndName implements Comparable {

		private Image image;
		private String name;

		private ImageAndName(String name, Image image) {
			this.image = image;
			this.name = name;
		}

		public boolean equal(String arg0) {
			return name.compareTo(arg0) == 0;
		}

		@Override
		public int compareTo(Object arg0) {
			return name.compareTo((String) arg0);
		}

		public Image getImage() {
			return image;
		}
	}

	// SUMMER
	public ArrayList<ImageAndName> summerResources = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> summerBuildings = new ArrayList<ImageAndName>();
	// private ImageAndName summerWater;

	// WINTER
	public ArrayList<ImageAndName> winterResources = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> winterBuildings = new ArrayList<ImageAndName>();
	// private ImageAndName winterWater;

	public ArrayList<ImageAndName> workers = new ArrayList<>();

	// WATER FOO KYLE HAS WEIRD COMMENTS SO I AM TOOOOO - LOVE SANTA CLAUSE
	public ArrayList<ImageAndName> winterWater = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> summerWater = new ArrayList<ImageAndName>();

	// BEACH BITCH
	public ArrayList<ImageAndName> winterSand = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> summerSand = new ArrayList<ImageAndName>();

	public ListOfImages() {
		// LETS LOAD SOME FUCKING IMAGES!!!
		for (int i = 1; i <= 4; i++) {
			// TREES!
			try {
				Image temp = ImageIO.read(new File(
						"./Graphics/Trees/Tree Redux_" + i + ".png"));
				summerResources.add(new ImageAndName(
						"./Graphics/Trees/Tree Redux_" + i + ".png", temp));
				temp = ImageIO.read(new File("./Graphics/Trees/Tree Redux_" + i
						+ "_Winter.png"));
				winterResources.add(new ImageAndName(
						"./Graphics/Trees/Tree Redux_" + i + "_Winter.png",
						temp));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// FISH LETS GO!!!
		try {
			Image temp = ImageIO.read(new File("./Graphics/Water/fish.png"));
			summerResources.add(new ImageAndName("./Graphics/Water/fish.png",
					temp));
			temp = ImageIO.read(new File("./Graphics/Water/fishWinter.png"));
			winterResources.add(new ImageAndName(
					"./Graphics/Water/fishWinter.png", temp));
			temp = ImageIO.read(new File("./Graphics/Water/fishSalty.png"));
			winterResources.add(new ImageAndName(
					"./Graphics/Water/fishSalty.png", temp));
			summerResources.add(new ImageAndName(
					"./Graphics/Water/fishSalty.png", temp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// BERRY BUSHES are just OK
		try {
			Image temp = ImageIO.read(new File(
					"./Graphics/BerryBushes/BerryBush_1.png"));
			summerResources.add(new ImageAndName(
					"./Graphics/BerryBushes/BerryBush_1.png", temp));
			// temp = ImageIO.read(new
			// File("./Graphics/BerryBushes/BerryBush_1_Winter.png"));
			winterResources.add(new ImageAndName(
					"./Graphics/BerryBushes/BerryBush_1.png", temp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Stoned are stones
		try {
			Image temp = ImageIO.read(new File("./Graphics/Stone/Stone_1.png"));
			summerResources.add(new ImageAndName("./Graphics/Stone/Stone_1.png", temp));
			// temp = ImageIO.read(new
			// File("./Graphics/Stone/Stone_1_Winter.png"));
			winterResources.add(new ImageAndName("./Graphics/Stone/Stone_1.png", temp));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// WATER LET IT FLO
		try {
			// SUMMER
			for (int i = 1; i < 10; i++) {
				Image temp = ImageIO.read(new File("./Graphics/Water/river_0"+ i + ".png"));
				summerWater.add(new ImageAndName("./Graphics/Water/river_0" + i+ ".png", temp));
			}
			// WINTER
			for (int i = 1; i < 10; i++) {
				Image temp = ImageIO.read(new File("./Graphics/Water/wRiver_0"+ i + ".png"));
				winterWater.add(new ImageAndName("./Graphics/Water/wRiver_0"+ i + ".png", temp));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// BEACHN IT UP!!!!
		try {
			// SUMMER LEFT and full sand and water
			for (int i = 1; i < 9; i++) {
				Image temp = ImageIO.read(new File("./Graphics/Water/sand_0"+ i + ".png"));
				summerSand.add(new ImageAndName("./Graphics/Water/sand_0" + i+ ".png", temp));
			}
			// SUMMER BOTTOM
			for (int i = 1; i < 9; i++) {
				if (i != 2 && i != 6) {
					Image temp = ImageIO.read(new File(	"./Graphics/Water/sand_0" + i + "B.png"));
					summerSand.add(new ImageAndName("./Graphics/Water/sand_0"+ i + "B.png", temp));
				}
			}
			// SUMMER RIGHT
			for (int i = 1; i < 9; i++) {
				if (i != 2 && i != 6) {
					Image temp = ImageIO.read(new File("./Graphics/Water/sand_0" + i + "R.png"));
					summerSand.add(new ImageAndName("./Graphics/Water/sand_0"+ i + "R.png", temp));
				}
			}
			// SUMMER TOP
			for (int i = 1; i < 9; i++) {
				if (i != 2 && i != 6) {
					Image temp = ImageIO.read(new File("./Graphics/Water/sand_0" + i + "T.png"));
					summerSand.add(new ImageAndName("./Graphics/Water/sand_0"+ i + "T.png", temp));
				}
			}

			// WINTER LEFT
			for (int i = 1; i < 9; i++) {
				Image temp = ImageIO.read(new File("./Graphics/Water/wSand_0"+ i + ".png"));
				winterSand.add(new ImageAndName("./Graphics/Water/wSand_0" + i+ ".png", temp));
			}
			// WINTER BOTTOM
			for (int i = 1; i < 9; i++) {
				if (i != 2 && i != 6) {
					Image temp = ImageIO.read(new File("./Graphics/Water/wSand_0" + i + "B.png"));
					winterSand.add(new ImageAndName("./Graphics/Water/wSand_0"+ i + "B.png", temp));
				}
			}
			// WINTER RIGHT
			for (int i = 1; i < 9; i++) {
				if (i != 2 && i != 6) {
					Image temp = ImageIO.read(new File("./Graphics/Water/wSand_0" + i + "R.png"));
					winterSand.add(new ImageAndName("./Graphics/Water/wSand_0"+ i + "R.png", temp));
				}
			}
			// WINTER TOP
			for (int i = 1; i < 9; i++) {
				if (i != 2 && i != 6) {
					Image temp = ImageIO.read(new File("./Graphics/Water/wSand_0" + i + "T.png"));
					winterSand.add(new ImageAndName("./Graphics/Water/wSand_0"+ i + "T.png", temp));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// LETS GET SUM BUILDINGS
		try {
			Image temp = ImageIO.read(new File(
					"./Graphics/Buildings/bridge.png"));
			summerBuildings.add(new ImageAndName(
					"./Graphics/Buildings/bridge.png", temp));
			temp = ImageIO.read(new File("./Graphics/Buildings/house.png"));
			summerBuildings.add(new ImageAndName(
					"./Graphics/Buildings/house.png", temp));
			temp = ImageIO.read(new File("./Graphics/Buildings/storage.png"));
			summerBuildings.add(new ImageAndName(
					"./Graphics/Buildings/storage.png", temp));
			temp = ImageIO.read(new File("./Graphics/Buildings/townHall.png"));
			summerBuildings.add(new ImageAndName(
					"./Graphics/Buildings/townHall.png", temp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Image temp = ImageIO
					.read(new File("./Graphics/Workers/male_1.png"));
			workers.add(new ImageAndName("./Graphics/Workers/male_1.png", temp));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Image getWorker(String fileName, boolean isWinter) {
		for (int i = 0; i < workers.size(); i++) {
			if (workers.get(i).equal(fileName))
				return workers.get(i).getImage();
		}
		return null;
	}

	public Image getResource(String fileName, boolean isWinter) {
		if (!isWinter) {
			for (int i = 0; i < summerResources.size(); i++) {
				if (summerResources.get(i).equal(fileName))
					return summerResources.get(i).getImage();
			}
		}
		for (int i = 0; i < winterResources.size(); i++) {
			if (winterResources.get(i).equal(fileName))
				return winterResources.get(i).getImage();
		}

		try {
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Image getBuilding(String fileName, boolean isWinter) {
		if (!isWinter) {
			for (int i = 0; i < summerBuildings.size(); i++) {
				if (summerBuildings.get(i).equal(fileName))
					return summerBuildings.get(i).getImage();
			}
		}
		for (int i = 0; i < winterBuildings.size(); i++) {
			if (winterBuildings.get(i).equal(fileName))
				return winterBuildings.get(i).getImage();
		}

		try {
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Image getWater(String fileName, boolean isWinter) {
		try {
			// is winter
			if (isWinter) {
				fileName = fileName.replace("river", "wRiver");
				// System.out.println(fileName);
				for (int i = 0; i < winterWater.size(); i++) {
					if (winterWater.get(i).equal(fileName))
						return winterWater.get(i).getImage();
				}
			}
			// summer
			else {
				for (int i = 0; i < summerWater.size(); i++) {
					if (summerWater.get(i).equal(fileName))
						return summerWater.get(i).getImage();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// GET SAND METHOD TO GET IMAGES FOR SAND
	public Image getSand(String fileName, boolean isWinter) {
		// is winter
		if (isWinter) {
			fileName = fileName.replace("sand", "wSand");
			// System.out.println(fileName);
			for (int i = 0; i < winterSand.size(); i++) {
				if (winterSand.get(i).equal(fileName))
					return winterSand.get(i).getImage();
			}
		}
		// summer
		else {
			for (int i = 0; i < summerSand.size(); i++) {
				if (summerSand.get(i).equal(fileName))
					return summerSand.get(i).getImage();
			}
		}

		try {
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
