package com.client.graphics.interfaces.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.client.graphics.interfaces.RSInterface;
import com.google.common.collect.Lists;

public class ColorPickerGroup {
	
	
	private List<ColorPicker> group = Lists.newArrayList();
	
	private int parentId;
	
	private ColorPickerGroup() {
		
	}
	public static ColorPickerGroup bind(RSInterface widget) {
		
		ColorPickerGroup cpg = new ColorPickerGroup();
		cpg.parentId = widget.id;
		for (int childId : widget.children) {
			RSInterface child = RSInterface.interfaceCache[childId];
			if (child == null || child.colorPicker == null)
				continue;
			cpg.group.add(child.colorPicker);
			child.colorPicker.setGroup(cpg);
		}
		
		return cpg;
	}
	
	public void onExpand(ColorPicker picker) {
		group.stream().filter(other -> other != picker).forEach(other -> {
			System.out.println("setclickablefalse");
			other.setExpanded(false);
			other.setClickable(false);
		});
		picker.setExpanded(true);
		picker.setClickable(true);
		RSInterface parent = RSInterface.interfaceCache[parentId];
		int firstIndex = -1;
		for(int index = 0;index<parent.children.length;index++) {
			int childId = parent.children[index];
			RSInterface child = RSInterface.interfaceCache[childId];
			if(child != null && child.type == RSInterface.TYPE_COLOR_PICKER) {
				firstIndex = index;
				break;
			}
		}
		
		BiFunction<RSInterface, Integer, Integer> getIndex = (parentWidget, id) -> {
			for(int index = 0;index<parentWidget.children.length;index++) {
				int childId = parentWidget.children[index];
				RSInterface child = RSInterface.interfaceCache[childId];
				if(child != null && child.id == id) {
					return index;
				}
			}
			return -1;
		};
		int pickerIndex = getIndex.apply(parent, picker.getId());
		int[] otherIndexes = group.stream().filter(other -> other != picker).mapToInt(other -> getIndex.apply(parent, other.getId())).toArray();
		int[] oldChildren = Arrays.copyOf(parent.children, parent.children.length);
		int[] oldX = Arrays.copyOf(parent.childX, parent.children.length);
		int[] oldY = Arrays.copyOf(parent.childY, parent.children.length);
		
		for(int index = 0;index<otherIndexes.length;index++) {
			parent.children[firstIndex] = oldChildren[otherIndexes[index]];
			parent.childX[firstIndex] = oldX[otherIndexes[index]];
			parent.childY[firstIndex] = oldY[otherIndexes[index]];
			firstIndex++;
		}
		parent.children[firstIndex] = oldChildren[pickerIndex];
		parent.childX[firstIndex] = oldX[pickerIndex];
		parent.childY[firstIndex] = oldY[pickerIndex];
	}
	
	public void onShrink(ColorPicker picker) {
		group.stream().forEach(other -> {
			other.setClickable(true);
			other.setExpanded(false);
		});
	}
	
	public void forEach(Consumer<ColorPicker> consumer) {
		group.stream().forEach(consumer::accept);
		
	}
	public void load(int[] colours) {
		for(int i = 0;i<colours.length;i++)
			group.get(i).setValue(colours[i]);
	}
	
	public int[] getColours() {
		return group.stream().mapToInt(ColorPicker::getValueInt).toArray();
	}

	public boolean anyExpanded() {
		return group.stream().anyMatch(ColorPicker::isExpanded);
	}
}
