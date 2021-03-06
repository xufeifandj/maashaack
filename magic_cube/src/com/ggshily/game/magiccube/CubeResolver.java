package com.ggshily.game.magiccube;

import com.ggshily.game.util.ArrayUtil;


public class CubeResolver
{
	private static final String STEP1 = 
			"   999\n" +
			"   919\n" +
			"   919\n" +
			"999909999999\n" +
			"922000339949\n" +
			"999909999999\n" +
			"   959\n" +
			"   959\n" +
			"   999";
	private static final String STEP2 = 
			"   999\n" +
			"   919\n" +
			"   111\n" +
			"992009399999\n" +
			"922000339949\n" +
			"992000399999\n" +
			"   555\n" +
			"   959\n" +
			"   999";
	private static final String STEP3 = 
			"   999\n" +
			"   111\n" +
			"   111\n" +
			"922009339999\n" +
			"922000339949\n" +
			"922000339999\n" +
			"   555\n" +
			"   555\n" +
			"   999";
	private static final String STEP4 = 
			"   999\n" +
			"   111\n" +
			"   111\n" +
			"922009339949\n" +
			"922000339444\n" +
			"922000339949\n" +
			"   555\n" +
			"   555\n" +
			"   999";
	private static final String STEP5 = 
			"   999\n" +
			"   111\n" +
			"   111\n" +
			"922000339444\n" +
			"922000339444\n" +
			"922000339444\n" +
			"   555\n" +
			"   555\n" +
			"   999";
	private static final String STEP6 = 
			"   191\n" +
			"   111\n" +
			"   111\n" +
			"222009333444\n" +
			"922000339444\n" +
			"222000333444\n" +
			"   555\n" +
			"   555\n" +
			"   595";
	
	
	private static final Vertex[] EDGE_BLOCK_POSITION = {
		// front
		new Vertex(1, 0, 0),
		new Vertex(0, 0, 1),
		new Vertex(1, 0, 2),
		new Vertex(2, 0, 1),
		
		// middle y
		new Vertex(0, 1, 0),
		new Vertex(0, 1, 2),
		new Vertex(2, 1, 2),
		new Vertex(2, 1, 0),
		
		// back
		new Vertex(1, 2, 0),
		new Vertex(0, 2, 1),
		new Vertex(1, 2, 2),
		new Vertex(2, 2, 1),
	};
	
	private static final String[] FRONT_TOP_EDGE_METHOD = {
		"",
		"L'U'",
		"D2B2U2",
		"RU",
		
		"U'",
		"L2U'L2",
		"R2UR2",
		"U",
		
		"U2",
		"B'U2",
		"B2U2",
		"BU2"
	};
	
	private static final String FRONT_TOP_EDGE_COLOR_EXCHANGE = "UL'B'LU2";
	
	private static final Vertex[] CORNER_BLOCK_POSITION = {
		new Vertex(0, 0, 0),
		new Vertex(0, 0, 2),
		new Vertex(2, 0, 2),
		new Vertex(2, 0, 0),
		
		new Vertex(0, 2, 0),
		new Vertex(0, 2, 2),
		new Vertex(2, 2, 2),
		new Vertex(2, 2, 0),
	};
	
	private static final String[] FRONT_TOP_LEFT_CORNER_METHOD = {
		"",
		"LBL'B'UB'U'",
		"R'BRB2UBU'",
		"U'BUBUBU'",
		
		"UBU'",
		"B'UBU'",
		"B2UBU'",
		"BUBU'"
	};
	
	private static final String FRONT_TOP_LEFT_CORNER_COLOR_EXCHANGE1 = "UBU'B'UBU'";
	private static final String FRONT_TOP_LEFT_CORNER_COLOR_EXCHANGE2 = "UB'U'BUB'U'";
	
	private static final String[] MIDDLE_TOP_LEFT_EDGE_METHOD = {
		"",
		"",
		"",
		"",
		
		"",
		"L'B'L'B'L'BLBLBU'B'U'B'U'BUBU",
		"RBRBRB'R'B'R'B'U'B'U'B'U'BUBU",
		"R'B'R'B'R'BRBRB'U'B'U'B'U'BUBU",
		
		"U'B'U'B'U'BUBU",
		"B'U'B'U'B'U'BUBU",
		"B2U'B'U'B'U'BUBU",
		"BU'B'U'B'U'BUBU",
	};
	private static final String MIDDLE_TOP_LEFT_EDGE_COLOR_EXCHANGE = "U'B'U'B'U'BUBUB'LBLBLB'L'B'L'";
	
	private static final String BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE = "FRUR'U'F'";
	
	private static final int[] BACK_CORNER_BLOCKS_COLOR_TEMPLATE_DONE = 
		{-1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		  4,  4,  4,  4,  4,  4,  4,  4,  4,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		};
	private static final int[] BACK_CORNER_BLOCKS_COLOR_TEMPLATE_1 = 
		{-1, -1, -1, -1, -1, -1, -1, -1, -1,
		  4, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1,  4, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1,  4, -1, -1, -1,  4,
		 -1, -1, -1, -1, -1, -1, -1, -1,  4,
		};
	private static final int[] BACK_CORNER_BLOCKS_COLOR_TEMPLATE_2 = 
		{-1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1,  4, -1, -1, -1, -1, -1, -1,
		  4, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1,  4,
		 -1, -1, -1, -1,  4, -1, -1, -1,  4,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		};
	private static final String BACK_CORNER_BLOCKS_COLOR_EXCHANGE1 = "R'U'RU'R'U'2R";
	private static final String BACK_CORNER_BLOCKS_COLOR_EXCHANGE2 = "FUF'UFU2F'";
	private static final int[] BACK_CORNER_BLOCKS_COLOR_TEMPLATE_3 = 
		{-1, -1, -1, -1, -1, -1, -1, -1, -1,
		  4, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		  4, -1, -1, -1,  4, -1,  4, -1, -1,
		 -1, -1, -1, -1, -1, -1,  4, -1, -1,
		};
	private static final int[] BACK_CORNER_BLOCKS_COLOR_TEMPLATE_4 = 
		{-1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		  4, -1,  4, -1,  4, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1,  4, -1,  4,
		};
	private static final int[] BACK_CORNER_BLOCKS_COLOR_TEMPLATE_5 = 
		{-1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1,  4, -1, -1, -1, -1, -1, -1,
		 -1, -1,  4, -1,  4, -1,  4, -1, -1,
		 -1, -1, -1, -1, -1, -1,  4, -1, -1,
		};
	private static final int[] BACK_CORNER_BLOCKS_COLOR_TEMPLATE_6 = 
		{-1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1,  4, -1, -1, -1, -1, -1, -1,
		  4, -1, -1, -1, -1, -1,  4, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1,  4, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1,  4,
		};
	private static final int[] BACK_CORNER_BLOCKS_COLOR_TEMPLATE_7 = 
		{-1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		  4, -1, -1, -1, -1, -1,  4, -1, -1,
		 -1, -1,  4, -1, -1, -1, -1, -1,  4,
		 -1, -1, -1, -1,  4, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1,
		};

	private static final String BACK_CORNER_BLOCKS_POSITION_EXCHANGE = "R2D2R'U'RD2R'UR'";
	
	public static String resolve(Cube cube)
	{
		String result = "";
		
		// 1st step: correct front four edge blocks
		result += correctFrontTopEdgeBlock(cube) + "\n";
		result += correctFrontLeftEdgeBlock(cube) + "\n";
		result += correctFrontDownEdgeBlock(cube) + "\n";
		result += correctFrontRightEdgeBlock(cube) + "\n";
		
		// 2nd step: correct front four corner blocks
		result += correctFrontTopLeftBlock(cube) + "\n";
		result += correctFrontLeftDownBlock(cube) + "\n";
		result += correctFrontRightDownBlock(cube) + "\n";
		result += correctFrontRightTopBlock(cube) + "\n";
		
		// 3th step: correct middle four edge blocks
		result += correctMiddleTopLeftBlock(cube) + "\n";
		result += correctMiddleLeftDownBlock(cube) + "\n";
		result += correctMiddleRightDownBlock(cube) + "\n";
		result += correctMiddleRightTopBlock(cube) + "\n";
		
		// 4th step: correct back four edge blocks' back color
		result += correctBackEdgeBlocksBackColor(cube) + "\n";
		
		// 5th step: correct back corner blocks' back color
		result += correctBackCornerBlocksBackColor(cube) + "\n";
		
		// 6th step: correct back corner blocks' position
		result += correctBackCornerBlocksPosition(cube) + "\n";
		
		// 7th step: correct back edge blocks' position
		result += correctBackEdgeBlocksPosition(cube) + "\n";
		
		return result;
	}

	public static String correctFrontTopEdgeBlock(Cube cube)
	{
		String result = "";
		
		Block frontCenter = cube.getFrontBlocks()[4];
		Block upperCenter = cube.getUpperBlocks()[4];
		
		int frontColor = frontCenter.getFrontSurface().get_colorIndex();
		int upperColor = upperCenter.getUpperSurface().get_colorIndex();
		
		Block frontTopCenterBlock = cube.getEdgeBlock(frontColor, upperColor);
		
		Vertex base = new Vertex(0, 0, 0);
		frontTopCenterBlock.getBasePoint(base);
		cube.transformBasePoint(base);
		for(int i = 0; i < EDGE_BLOCK_POSITION.length; i++)
		{
			if(base.equals(EDGE_BLOCK_POSITION[i]))
			{
				result += FRONT_TOP_EDGE_METHOD[i];
				
				executeCommands(cube, FRONT_TOP_EDGE_METHOD[i]);
				break;
			}
		}
		if(cube.getFrontBlocks()[1].getFrontSurface().get_colorIndex() != frontColor)
		{
			result += FRONT_TOP_EDGE_COLOR_EXCHANGE;
			
			executeCommands(cube, FRONT_TOP_EDGE_COLOR_EXCHANGE);
		}
		return result;
	}

	public static String correctFrontLeftEdgeBlock(Cube cube)
	{
		cube.rotateYNegative90();
		String result = rotateCommandY90(correctFrontTopEdgeBlock(cube));
		cube.rotateY90();
		return result;
	}

	public static String correctFrontDownEdgeBlock(Cube cube)
	{
		cube.rotateY90();
		cube.rotateY90();
		String result = rotateCommandY90(rotateCommandY90(correctFrontTopEdgeBlock(cube)));
		cube.rotateY90();
		cube.rotateY90();
		return result;
	}

	public static String correctFrontRightEdgeBlock(Cube cube)
	{
		cube.rotateY90();
		String result = rotateCommandY90(rotateCommandY90(rotateCommandY90(correctFrontTopEdgeBlock(cube))));
		cube.rotateYNegative90();
		
		int[] data = CubeUtil.string2Array(STEP1);
		ArrayUtil.replace(data, 9, -1);
		Cube temp = CubeFactory.createCube(data);
		if(!CubeUtil.isMatch(temp, cube))
		{
			throw new Error();
		}
		
		return result;
	}
	
	public static String correctFrontTopLeftBlock(Cube cube)
	{
		String result = "";
		
		Block frontCenter = cube.getFrontBlocks()[4];
		Block leftCenter = cube.getLeftBlocks()[4];
		Block upperCenter = cube.getUpperBlocks()[4];
		
		int frontColor = frontCenter.getFrontSurface().get_colorIndex();
		int leftColor = leftCenter.getLeftSurface().get_colorIndex();
		int upperColor = upperCenter.getUpperSurface().get_colorIndex();
		
		Block frontTopLeftBlock = cube.getCornerBlock(frontColor, leftColor, upperColor);
		
		Vertex base = new Vertex(0, 0, 0);
		frontTopLeftBlock.getBasePoint(base);
		cube.transformBasePoint(base);
		for(int i = 0; i < CORNER_BLOCK_POSITION.length; i++)
		{
			if(base.equals(CORNER_BLOCK_POSITION[i]))
			{
				result += FRONT_TOP_LEFT_CORNER_METHOD[i];
				executeCommands(cube, FRONT_TOP_LEFT_CORNER_METHOD[i]);
				break;
			}
		}
		
		if(frontTopLeftBlock.getFrontSurface().get_colorIndex() == leftColor &&
				frontTopLeftBlock.getLeftSurface().get_colorIndex() == upperColor)
		{
			result += FRONT_TOP_LEFT_CORNER_COLOR_EXCHANGE1;
			executeCommands(cube, FRONT_TOP_LEFT_CORNER_COLOR_EXCHANGE1);
		}
		else if(frontTopLeftBlock.getFrontSurface().get_colorIndex() == upperColor &&
				frontTopLeftBlock.getLeftSurface().get_colorIndex() == frontColor)
		{
			result += FRONT_TOP_LEFT_CORNER_COLOR_EXCHANGE2;
			executeCommands(cube, FRONT_TOP_LEFT_CORNER_COLOR_EXCHANGE2);
		}
		else if(frontTopLeftBlock.getFrontColor() == frontColor &&
				frontTopLeftBlock.getUpperColor() == upperColor &&
				frontTopLeftBlock.getLeftColor() == leftColor)
		{
			// done
		}
		else
		{
			throw new Error("The kubic is broken:\n" + cube.toString());
		}
		
		return result;
	}
	
	public static String correctFrontLeftDownBlock(Cube cube)
	{
		String result = "";
		
		cube.rotateYNegative90();
		result = rotateCommandY90(correctFrontTopLeftBlock(cube));
		cube.rotateY90();
		
		return result;
	}
	
	public static String correctFrontRightDownBlock(Cube cube)
	{
		String result = "";
		
		cube.rotateY90();
		cube.rotateY90();
		result = rotateCommandY90(rotateCommandY90(correctFrontTopLeftBlock(cube)));
		cube.rotateY90();
		cube.rotateY90();
		
		return result;
	}
	
	public static String correctFrontRightTopBlock(Cube cube)
	{
		String result = "";
		
		cube.rotateY90();
		result = rotateCommandY90(rotateCommandY90(rotateCommandY90(correctFrontTopLeftBlock(cube))));
		cube.rotateYNegative90();
		
		int[] data = CubeUtil.string2Array(STEP2);
		ArrayUtil.replace(data, 9, -1);
		Cube temp = CubeFactory.createCube(data);
		if(!CubeUtil.isMatch(temp, cube))
		{
			throw new Error();
		}
		
		return result;
	}
	
	public static String correctMiddleTopLeftBlock(Cube cube)
	{
		String result = null;

		Block leftCenter = cube.getLeftBlocks()[4];
		Block upperCenter = cube.getUpperBlocks()[4];
		
		int leftColor = leftCenter.getLeftSurface().get_colorIndex();
		int upperColor = upperCenter.getUpperSurface().get_colorIndex();
		
		Block middleTopLeftBlock = cube.getEdgeBlock(leftColor, upperColor);
		
		Vertex base = new Vertex(0, 0, 0);
		middleTopLeftBlock.getBasePoint(base);
		cube.transformBasePoint(base);
		for(int i = 0; i < EDGE_BLOCK_POSITION.length; i++)
		{
			if(base.equals(EDGE_BLOCK_POSITION[i]))
			{
				result = MIDDLE_TOP_LEFT_EDGE_METHOD[i];
				
				executeCommands(cube, MIDDLE_TOP_LEFT_EDGE_METHOD[i]);
				break;
			}
		}
		if(middleTopLeftBlock.getUpperSurface().get_colorIndex() != upperColor)
		{
			result += MIDDLE_TOP_LEFT_EDGE_COLOR_EXCHANGE;
			
			executeCommands(cube, MIDDLE_TOP_LEFT_EDGE_COLOR_EXCHANGE);
		}
		if(result == null)
		{
			throw new Error("the kubic is wrong:\n" + cube.toString());
		}

		return result;
	}
	
	public static String correctMiddleLeftDownBlock(Cube cube)
	{
		String result = "";
		
		cube.rotateYNegative90();
		result = rotateCommandY90(correctMiddleTopLeftBlock(cube));
		cube.rotateY90();
		
		return result;
	}
	
	public static String correctMiddleRightDownBlock(Cube cube)
	{
		String result = "";
		
		cube.rotateY90();
		cube.rotateY90();
		result = rotateCommandY90(rotateCommandY90(correctMiddleTopLeftBlock(cube)));
		cube.rotateY90();
		cube.rotateY90();
		
		return result;
	}
	
	public static String correctMiddleRightTopBlock(Cube cube)
	{
		String result = "";
		
		cube.rotateY90();
		result = rotateCommandY90(rotateCommandY90(rotateCommandY90(correctMiddleTopLeftBlock(cube))));
		cube.rotateYNegative90();
		
		int[] data = CubeUtil.string2Array(STEP3);
		ArrayUtil.replace(data, 9, -1);
		Cube temp = CubeFactory.createCube(data);
		if(!CubeUtil.isMatch(temp, cube))
		{
			throw new Error();
		}
		
		return result;
	}

	public static String correctBackEdgeBlocksBackColor(Cube cube)
	{
		String result = "";
		
		Block[] backBlocks = cube.getBackBlocks();
		int backColor = backBlocks[4].getBackSurface().get_colorIndex();
		
		if(backBlocks[1].getBackColor() == backColor &&
				backBlocks[3].getBackColor() == backColor &&
				backBlocks[5].getBackColor() == backColor &&
				backBlocks[7].getBackColor() == backColor)
		{
			// done
		}
		else if(backBlocks[1].getBackColor() == backColor &&
				backBlocks[3].getBackColor() != backColor &&
				backBlocks[5].getBackColor() != backColor &&
				backBlocks[7].getBackColor() == backColor)
		{
			cube.rotateY90();
			cube.rotateXNegative90();
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result = rotateCommandY90(rotateCommandY90(rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE))));
			cube.rotateX90();
			cube.rotateYNegative90();
		}
		else if(backBlocks[1].getBackColor() != backColor &&
				backBlocks[3].getBackColor() == backColor &&
				backBlocks[5].getBackColor() == backColor &&
				backBlocks[7].getBackColor() != backColor)
		{
			cube.rotateXNegative90();
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result = rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			cube.rotateX90();
		}
		else if(backBlocks[1].getBackColor() != backColor &&
				backBlocks[3].getBackColor() != backColor &&
				backBlocks[5].getBackColor() != backColor &&
				backBlocks[7].getBackColor() != backColor)
		{
			cube.rotateXNegative90();
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result = rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);

			cube.rotateZ90();
			cube.rotateZ90();
			
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result += rotateCommandY90(rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE)));
			result += rotateCommandY90(rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE)));

			cube.rotateZ90();
			cube.rotateZ90();
			
			cube.rotateX90();
		}
		else if(backBlocks[1].getBackColor() == backColor &&
				backBlocks[3].getBackColor() == backColor &&
				backBlocks[5].getBackColor() != backColor &&
				backBlocks[7].getBackColor() != backColor)
		{
			cube.rotateY90();
			cube.rotateY90();
			
			cube.rotateXNegative90();
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result = rotateCommandY90(rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE)));
			result += rotateCommandY90(rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE)));
			cube.rotateX90();

			cube.rotateY90();
			cube.rotateY90();
		}
		else if(backBlocks[1].getBackColor() == backColor &&
				backBlocks[3].getBackColor() != backColor &&
				backBlocks[5].getBackColor() == backColor &&
				backBlocks[7].getBackColor() != backColor)
		{
			cube.rotateY90();

			cube.rotateXNegative90();
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result = rotateCommandY90(rotateCommandY90(rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE))));
			result += rotateCommandY90(rotateCommandY90(rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE))));
			cube.rotateX90();
			
			cube.rotateYNegative90();
		}
		else if(backBlocks[1].getBackColor() != backColor &&
				backBlocks[3].getBackColor() == backColor &&
				backBlocks[5].getBackColor() != backColor &&
				backBlocks[7].getBackColor() == backColor)
		{
			cube.rotateYNegative90();

			cube.rotateXNegative90();
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result = rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE));
			result += rotateCommandY90(rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE));
			cube.rotateX90();

			cube.rotateY90();
		}
		else if(backBlocks[1].getBackColor() != backColor &&
				backBlocks[3].getBackColor() != backColor &&
				backBlocks[5].getBackColor() == backColor &&
				backBlocks[7].getBackColor() == backColor)
		{
			cube.rotateXNegative90();
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			executeCommands(cube, BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result = rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			result += rotateCommandX90(BACK_EDGE_BLOCKS_BACK_COLOR_EXCHANGE);
			cube.rotateX90();
		}
		else
		{
			throw new Error("The kubic is broken:\n" + cube.toString());
		}
		
		int[] data = CubeUtil.string2Array(STEP4);
		ArrayUtil.replace(data, 9, -1);
		Cube temp = CubeFactory.createCube(data);
		if(!CubeUtil.isMatch(temp, cube))
		{
			throw new Error();
		}
		
		return result;
	}

	public static String correctBackCornerBlocksBackColor(Cube cube)
	{
		String result = "";
		
		Block[] backBlocks = cube.getBackBlocks();
		int backColor = backBlocks[4].getBackColor();
		
		int[] data = BACK_CORNER_BLOCKS_COLOR_TEMPLATE_DONE.clone();
		
		if(isBackCornerBlocksBackColorComplete(cube))
		{
			// done
		}
		else
		{
			int count = 0;
			if(backBlocks[0].getBackColor() == backColor)
				count++;
			if(backBlocks[2].getBackColor() == backColor)
				count++;
			if(backBlocks[6].getBackColor() == backColor)
				count++;
			if(backBlocks[8].getBackColor() == backColor)
				count++;
			
			if(count == 1)
			{
				result = correctBackCornerBlocksBackColor1(cube, backColor);
			}
			else
			{
				int[][] conditions = {BACK_CORNER_BLOCKS_COLOR_TEMPLATE_3,
						BACK_CORNER_BLOCKS_COLOR_TEMPLATE_4,
						BACK_CORNER_BLOCKS_COLOR_TEMPLATE_5,
						BACK_CORNER_BLOCKS_COLOR_TEMPLATE_6,
						BACK_CORNER_BLOCKS_COLOR_TEMPLATE_7
				};
				
				int rotateCount = -1;
				for(int i = 0; i < conditions.length; ++i)
				{
					data = conditions[i];
					ArrayUtil.replace(data, 4, backColor);
					rotateCount = CubeUtil.isMathAfterRotateY(cube, CubeFactory.createCube(data));
					if(rotateCount != -1)
					{
						break;
					}
				}
				
				if(rotateCount != -1)
				{
					cube.rotateXNegative90();
					executeCommands(cube, BACK_CORNER_BLOCKS_COLOR_EXCHANGE1);
					result = rotateCommandX90(BACK_CORNER_BLOCKS_COLOR_EXCHANGE1);
					cube.rotateX90();

					for(int i = rotateCount; i < 4; ++i)
					{
						cube.rotateY90();
						result = rotateCommandY90(result);
					}

					result += correctBackCornerBlocksBackColor1(cube, backColor);
				}
				else
				{
					throw new Error("The kubic is broken:\n" + cube.toString());
				}
			}
		}
		
		int[] data1 = CubeUtil.string2Array(STEP5);
		ArrayUtil.replace(data1, 9, -1);
		Cube temp = CubeFactory.createCube(data1);
		if(!CubeUtil.isMatch(temp, cube))
		{
			throw new Error();
		}
		
		return result;
	}

	private static boolean isBackCornerBlocksBackColorComplete(Cube cube)
	{
		Block[] backBlocks = cube.getBackBlocks();
		int backColor = backBlocks[4].getBackColor();
		
		return backBlocks[0].getBackColor() == backColor &&
				backBlocks[2].getBackColor() == backColor &&
				backBlocks[6].getBackColor() == backColor &&
				backBlocks[8].getBackColor() == backColor;
	}

	private static String correctBackCornerBlocksBackColor1(Cube cube, int backColor)
	{
		String result = null;
		int[] data;
		data = BACK_CORNER_BLOCKS_COLOR_TEMPLATE_1;
		ArrayUtil.replace(data, 4, backColor);
		int rotateCount1 = CubeUtil.isMathAfterRotateY(cube, CubeFactory.createCube(data));

		if(rotateCount1 != -1)
		{
			cube.rotateXNegative90();
			executeCommands(cube, BACK_CORNER_BLOCKS_COLOR_EXCHANGE1);
			result = rotateCommandX90(BACK_CORNER_BLOCKS_COLOR_EXCHANGE1);
			cube.rotateX90();
			
			for(int i = rotateCount1; i < 4; ++i)
			{
				cube.rotateY90();
				result = rotateCommandY90(result);
			}
		}
		else
		{
			data = BACK_CORNER_BLOCKS_COLOR_TEMPLATE_2;
			ArrayUtil.replace(data, 4, backColor);
			int rotateCount2 = CubeUtil.isMathAfterRotateY(cube, CubeFactory.createCube(data));
			
			if (rotateCount2 != -1)
			{
				cube.rotateXNegative90();
				executeCommands(cube, BACK_CORNER_BLOCKS_COLOR_EXCHANGE2);
				result = rotateCommandX90(BACK_CORNER_BLOCKS_COLOR_EXCHANGE2);
				cube.rotateX90();

				for (int i = rotateCount2; i < 4; ++i)
				{
					cube.rotateY90();
					result = rotateCommandY90(result);
				}
			} else
			{
				throw new Error("The kubic is broken:\n" + cube.toString());
			}
		}
		return result;
	}

	public static String correctBackCornerBlocksPosition(Cube cube)
	{
		String result = "";
		
		String method = getMethod2FinishBackCornerBlocks(cube);
		if(method != null)
		{
			result = method;
		}
		else
		{
			method = getMethod2GetSameCornerColorInOneFace(cube);
			if(method != null)
			{
				result = method;

				cube.rotateX90();
				cube.rotateX90();
				executeCommands(cube, BACK_CORNER_BLOCKS_POSITION_EXCHANGE);
				result += rotateCommandX90(rotateCommandX90(BACK_CORNER_BLOCKS_POSITION_EXCHANGE));
				cube.rotateX90();
				cube.rotateX90();
				
				method = getMethod2FinishBackCornerBlocks(cube);
				if(method != null)
				{
					result += method;
				}
				else
				{
					throw new Error("The kubic is broken:\n" + cube.toString());
				}
			}
			else
			{
				cube.rotateX90();
				cube.rotateX90();
				executeCommands(cube, BACK_CORNER_BLOCKS_POSITION_EXCHANGE);
				result += rotateCommandX90(rotateCommandX90(BACK_CORNER_BLOCKS_POSITION_EXCHANGE));
				cube.rotateX90();
				cube.rotateX90();
				
				method = getMethod2GetSameCornerColorInOneFace(cube);
				if(method == null)
				{
					throw new Error("The kubic is broken:\n" + cube.toString());
				}
				result += method;
				
				cube.rotateX90();
				cube.rotateX90();
				executeCommands(cube, BACK_CORNER_BLOCKS_POSITION_EXCHANGE);
				result += rotateCommandX90(rotateCommandX90(BACK_CORNER_BLOCKS_POSITION_EXCHANGE));
				cube.rotateX90();
				cube.rotateX90();
				
				method = getMethod2FinishBackCornerBlocks(cube);
				if(method != null)
				{
					result += method;
				}
				else
				{
					throw new Error("The kubic is broken:\n" + cube.toString());
				}
			}
		}
		
		int[] data = CubeUtil.string2Array(STEP6);
		ArrayUtil.replace(data, 9, -1);
		Cube temp = CubeFactory.createCube(data);
		if(!CubeUtil.isMatch(temp, cube))
		{
			throw new Error();
		}
		return result;
	}
	
	private static String getMethod2GetSameCornerColorInOneFace(Cube cube)
	{
		Block[] rightBlocks = cube.getRightBlocks();
		if(rightBlocks[2].getRightColor() == rightBlocks[8].getRightColor())
		{
			return "";
		}
		cube.B();
		rightBlocks = cube.getRightBlocks();
		if(rightBlocks[2].getRightColor() == rightBlocks[8].getRightColor())
		{
			return "B";
		}
		cube.B();
		rightBlocks = cube.getRightBlocks();
		if(rightBlocks[2].getRightColor() == rightBlocks[8].getRightColor())
		{
			return "B2";
		}
		cube.B();
		rightBlocks = cube.getRightBlocks();
		if(rightBlocks[2].getRightColor() == rightBlocks[8].getRightColor())
		{
			return "B'";
		}
		cube.B();
		return null;
	}

	private static String getMethod2FinishBackCornerBlocks(Cube cube)
	{
		if(checkIfBackCornerBlocksFinished(cube))
		{
			return "";
		}
		cube.B();
		if(checkIfBackCornerBlocksFinished(cube))
		{
			return "B";
		}
		cube.B();
		if(checkIfBackCornerBlocksFinished(cube))
		{
			return "B2";
		}
		cube.B();
		if(checkIfBackCornerBlocksFinished(cube))
		{
			return "B'";
		}
		cube.B();
		
		return null;
	}

	private static boolean checkIfBackCornerBlocksFinished(Cube cube)
	{
		Block[] upperBlocks = cube.getUpperBlocks();
		Block[] leftBlocks = cube.getLeftBlocks();
		Block[] downBlocks = cube.getDownBlocks();
		Block[] rightBlocks = cube.getRightBlocks();
		
		return (upperBlocks[0].getUpperColor() == upperBlocks[2].getUpperColor() && 
				upperBlocks[0].getUpperColor() == upperBlocks[4].getUpperColor() &&
				leftBlocks[0].getLeftColor() == leftBlocks[6].getLeftColor() && 
				leftBlocks[0].getLeftColor() == leftBlocks[4].getLeftColor() && 
				downBlocks[6].getDownColor() == downBlocks[8].getDownColor() && 
				downBlocks[6].getDownColor() == downBlocks[4].getDownColor() && 
				rightBlocks[2].getRightColor() == rightBlocks[8].getRightColor() && 
				rightBlocks[2].getRightColor() == rightBlocks[4].getRightColor());
	}

	public static String correctBackEdgeBlocksPosition(Cube cube)
	{
		String result = "";
		
		if(!checkIfBackEdgeBlocksFinished(cube))
		{
			String method1 = clockWiseCanFinishBackEdgeBlocks(cube);
			
			if(method1 != null)
			{
				result = method1;
			}
			else
			{
				String method2 = countClockWiseCanFinishBackEdgeBlocks(cube);
				if(method2 != null)
				{
					result = method2;
				}
				else
				{
					String method3 = exchange2OppositeCanFinishBackEdgeBlocks(cube);
					if(method3 != null)
					{
						result = method3;
					}
					else
					{
						String method4 = exchange2NeighborCanFinishBackEdgeBlocks(cube);
						if(method4 != null)
						{
							result = method4;
						}
						else
						{
							throw new Error("The kubic is broken:\n" + cube.toString());
						}
					}
				}
			}
		}
		return result;
	}

	private static String clockWiseCanFinishBackEdgeBlocks(Cube cube)
	{
		String result = null;
		int count = getRotateCountForClockWise(cube);
		if(count >= 0)
		{
			for(int j = 0; j < count; ++j)
			{
				cube.rotateY90();
			}
			
			cube.rotateXNegative90();
			
			String step = BACK_CORNER_BLOCKS_COLOR_EXCHANGE2 + rotateCommandZ90(rotateCommandZ90(BACK_CORNER_BLOCKS_COLOR_EXCHANGE1));
			executeCommands(cube, step);
			for(int j = 0; j < count; ++j)
			{
				step = rotateCommandYNegative90(step);
			}
			cube.rotateX90();
			
			result = rotateCommandX90(step);
			
			for(int j = count; j < 4; ++j)
			{
				cube.rotateY90();
			}
		}
		return result;
	}

	private static int getRotateCountForClockWise(Cube cube)
	{
		Block[] upperBlocks = cube.getUpperBlocks();
		Block[] leftBlocks = cube.getLeftBlocks();
		Block[] downBlocks = cube.getDownBlocks();
		Block[] rightBlocks = cube.getRightBlocks();
		
		int[] centerColors = {rightBlocks[4].getRightColor(), downBlocks[4].getDownColor(), leftBlocks[4].getLeftColor(), upperBlocks[4].getUpperColor()};
		int[] edgeColors = {rightBlocks[5].getRightColor(), downBlocks[7].getDownColor(), leftBlocks[3].getLeftColor(), upperBlocks[1].getUpperColor()};
		
		for(int i = 0; i < centerColors.length; ++i)
		{
			if(edgeColors[i] == centerColors[i] && edgeColors[(i + 1) % 4] == centerColors[(i + 3) % 4]
					&& edgeColors[(i + 2) % 4] == centerColors[(i + 1) % 4] && edgeColors[(i + 3) % 4] == centerColors[(i + 2) % 4])
			{
				return i;
			}
		}
		return -1;
	}

	private static String countClockWiseCanFinishBackEdgeBlocks(Cube cube)
	{
		String result = null;
		int count = getRotateCountForCountClockWise(cube);
		if(count >= 0)
		{
			for(int j = 0; j < count; ++j)
			{
				cube.rotateY90();
			}

			cube.rotateXNegative90();
			String step = BACK_CORNER_BLOCKS_COLOR_EXCHANGE1 + rotateCommandZ90(rotateCommandZ90(BACK_CORNER_BLOCKS_COLOR_EXCHANGE2));
			executeCommands(cube, step);
			for(int j = 0; j < count; ++j)
			{
				step = rotateCommandYNegative90(step);
			}
			cube.rotateX90();
			
			result = rotateCommandX90(step);
			
			for(int j = count; j < 4; ++j)
			{
				cube.rotateY90();
			}
		}
		return result;
	}

	private static int getRotateCountForCountClockWise(Cube cube)
	{
		Block[] upperBlocks = cube.getUpperBlocks();
		Block[] leftBlocks = cube.getLeftBlocks();
		Block[] downBlocks = cube.getDownBlocks();
		Block[] rightBlocks = cube.getRightBlocks();
		
		int[] centerColors = {upperBlocks[4].getUpperColor(), rightBlocks[4].getRightColor(), downBlocks[4].getDownColor(), leftBlocks[4].getLeftColor()};
		int[] edgeColors = {upperBlocks[1].getUpperColor(), rightBlocks[5].getRightColor(), downBlocks[7].getDownColor(), leftBlocks[3].getLeftColor()};
		
		for(int i = 0; i < centerColors.length; ++i)
		{
			if(edgeColors[i] == centerColors[i] && edgeColors[(i + 1) % 4] == centerColors[(i + 2) % 4]
					&& edgeColors[(i + 2) % 4] == centerColors[(i + 3) % 4] && edgeColors[(i + 3) % 4] == centerColors[(i + 1) % 4])
			{
				return i;
			}
		}
		return -1;
	}

	private static String exchange2OppositeCanFinishBackEdgeBlocks(Cube cube)
	{
		if(is2Opposite(cube))
		{
			String result = "";

			cube.rotateXNegative90();
			String step = BACK_CORNER_BLOCKS_COLOR_EXCHANGE1 + rotateCommandZ90(rotateCommandZ90(BACK_CORNER_BLOCKS_COLOR_EXCHANGE2));
			executeCommands(cube, step);
			cube.rotateX90();
			
			result += rotateCommandX90(step);
			
			
			String method1 = clockWiseCanFinishBackEdgeBlocks(cube);
			
			if(method1 != null)
			{
				result += method1;
			}
			else
			{
				String method2 = countClockWiseCanFinishBackEdgeBlocks(cube);
				if(method2 != null)
				{
					result += method2;
				}
				else
				{
					throw new Error("The kubic is broken:\n" + cube.toString());
				}
			}
			
			return result;
		}
		return null;
	}

	private static boolean is2Opposite(Cube cube)
	{
		Block[] upperBlocks = cube.getUpperBlocks();
		Block[] leftBlocks = cube.getLeftBlocks();
		Block[] downBlocks = cube.getDownBlocks();
		Block[] rightBlocks = cube.getRightBlocks();
		
		int[] centerColors = {upperBlocks[4].getUpperColor(), rightBlocks[4].getRightColor(), downBlocks[4].getDownColor(), leftBlocks[4].getLeftColor()};
		int[] edgeColors = {upperBlocks[1].getUpperColor(), rightBlocks[5].getRightColor(), downBlocks[7].getDownColor(), leftBlocks[3].getLeftColor()};
		
		return centerColors[0] == edgeColors[2] && centerColors[1] == edgeColors[3] && centerColors[2] == edgeColors[0] && centerColors[3] == edgeColors[1]; 
	}

	private static String exchange2NeighborCanFinishBackEdgeBlocks(Cube cube)
	{
		if(is2Neighbor(cube))
		{
			String result = "";

			cube.rotateXNegative90();
			String step = BACK_CORNER_BLOCKS_COLOR_EXCHANGE1 + rotateCommandZ90(rotateCommandZ90(BACK_CORNER_BLOCKS_COLOR_EXCHANGE2));
			executeCommands(cube, step);
			cube.rotateX90();
			
			result += rotateCommandX90(step);
			
			
			String method1 = clockWiseCanFinishBackEdgeBlocks(cube);
			
			if(method1 != null)
			{
				result += method1;
			}
			else
			{
				String method2 = countClockWiseCanFinishBackEdgeBlocks(cube);
				if(method2 != null)
				{
					result += method2;
				}
				else
				{
					throw new Error("The kubic is broken:\n" + cube.toString());
				}
			}
			
			return result;
		}
		return null;
	}

	private static boolean is2Neighbor(Cube cube)
	{
		Block[] upperBlocks = cube.getUpperBlocks();
		Block[] leftBlocks = cube.getLeftBlocks();
		Block[] downBlocks = cube.getDownBlocks();
		Block[] rightBlocks = cube.getRightBlocks();
		
		int[] centerColors = {upperBlocks[4].getUpperColor(), rightBlocks[4].getRightColor(), downBlocks[4].getDownColor(), leftBlocks[4].getLeftColor()};
		int[] edgeColors = {upperBlocks[1].getUpperColor(), rightBlocks[5].getRightColor(), downBlocks[7].getDownColor(), leftBlocks[3].getLeftColor()};
		
		return (centerColors[0] == edgeColors[3] && centerColors[3] == edgeColors[0] && centerColors[1] == edgeColors[2] && centerColors[2] == edgeColors[1]) ||
				(centerColors[0] == edgeColors[1] && centerColors[1] == edgeColors[0] && centerColors[2] == edgeColors[3] && centerColors[3] == edgeColors[2]); 
	}

	private static boolean checkIfBackEdgeBlocksFinished(Cube cube)
	{
		Block[] upperBlocks = cube.getUpperBlocks();
		Block[] leftBlocks = cube.getLeftBlocks();
		Block[] downBlocks = cube.getDownBlocks();
		Block[] rightBlocks = cube.getRightBlocks();
		
		return (upperBlocks[1].getUpperColor() == upperBlocks[4].getUpperColor() &&
				leftBlocks[3].getLeftColor() == leftBlocks[4].getLeftColor() && 
				downBlocks[7].getDownColor() == downBlocks[4].getDownColor() && 
				rightBlocks[5].getRightColor() == rightBlocks[4].getRightColor());
	}

	public static void executeCommands(Cube cube, String commands)
	{
		int i = 0;
		while(i < commands.length())
		{
			char command = commands.charAt(i);
			if(i + 1 < commands.length() && commands.charAt(i + 1) == '\'')
			{
				command += 1;
				i++;
			}
			boolean twice = false;
			if(i + 1 < commands.length() && commands.charAt(i + 1) == '2')
			{
				twice = true;
				i++;
			}
			executeSingleCommand(cube, command);
			if(twice)
				executeSingleCommand(cube, command);
			
			i++;
		}
	}

	public static void executeSingleCommand(Cube cube, char command)
	{
		switch(command)
		{
		case 'F':
			cube.F();
			break;
		case 'B':
			cube.B();
			break;
		case 'L':
			cube.L();
			break;
		case 'R':
			cube.R();
			break;
		case 'U':
			cube.U();
			break;
		case 'D':
			cube.D();
			break;
		case 'F' + 1:
			cube.F_CC();
			break;
		case 'B' + 1:
			cube.B_CC();
			break;
		case 'L' + 1:
			cube.L_CC();
			break;
		case 'R' + 1:
			cube.R_CC();
			break;
		case 'U' + 1:
			cube.U_CC();
			break;
		case 'D' + 1:
			cube.D_CC();
			break;
		default:
			throw new Error("unknow command:" + command);
		}
	}
	
	public static String rotateCommandX90(String commands)
	{
		char[] commandChars = commands.toCharArray();
		
		for(int i = 0; i < commandChars.length; i++)
		{
			switch(commandChars[i])
			{
			case 'F':
				commandChars[i] = 'U';
				break;
			case 'U':
				commandChars[i] = 'B';
				break;
			case 'B':
				commandChars[i] = 'D';
				break;
			case 'D':
				commandChars[i] = 'F';
				break;
			}
		}
		return String.valueOf(commandChars);
	}
	
	public static String rotateCommandXNegative90(String commands)
	{
		char[] commandChars = commands.toCharArray();
		
		for(int i = 0; i < commandChars.length; i++)
		{
			switch(commandChars[i])
			{
			case 'F':
				commandChars[i] = 'D';
				break;
			case 'U':
				commandChars[i] = 'F';
				break;
			case 'B':
				commandChars[i] = 'U';
				break;
			case 'D':
				commandChars[i] = 'B';
				break;
			}
		}
		return String.valueOf(commandChars);
	}
	
	public static String rotateCommandY90(String commands)
	{
		char[] commandChars = commands.toCharArray();
		
		for(int i = 0; i < commandChars.length; i++)
		{
			switch(commandChars[i])
			{
			case 'R':
				commandChars[i] = 'U';
				break;
			case 'U':
				commandChars[i] = 'L';
				break;
			case 'L':
				commandChars[i] = 'D';
				break;
			case 'D':
				commandChars[i] = 'R';
				break;
			}
		}
		return String.valueOf(commandChars);
	}
	
	public static String rotateCommandZ90(String commands)
	{
		char[] commandChars = commands.toCharArray();
		
		for(int i = 0; i < commandChars.length; i++)
		{
			switch(commandChars[i])
			{
			case 'F':
				commandChars[i] = 'R';
				break;
			case 'L':
				commandChars[i] = 'F';
				break;
			case 'B':
				commandChars[i] = 'L';
				break;
			case 'R':
				commandChars[i] = 'B';
				break;
			}
		}
		return String.valueOf(commandChars);
	}
	
	public static String rotateCommandYNegative90(String commands)
	{
		char[] commandChars = commands.toCharArray();
		
		for(int i = 0; i < commandChars.length; i++)
		{
			switch(commandChars[i])
			{
			case 'R':
				commandChars[i] = 'D';
				break;
			case 'U':
				commandChars[i] = 'R';
				break;
			case 'L':
				commandChars[i] = 'U';
				break;
			case 'D':
				commandChars[i] = 'L';
				break;
			}
		}
		return String.valueOf(commandChars);
	}
}
