/// @description 
if anim {
	anim_val = clamp(anim_val+0.02,0,1)
	image_xscale = 2 + ((anim_val*pocazatel))
	image_yscale = image_xscale;
	if(anim_val == 1) {
		anim_val = 0;
		pocazatel = -1 * pocazatel
	}
}

if md {
	x = mouse_x	
	y = mouse_y	
}