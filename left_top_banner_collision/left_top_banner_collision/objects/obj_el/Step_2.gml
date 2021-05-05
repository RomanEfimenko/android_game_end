/// @description Колиззия с баннером
if (!variable_instance_exists(id, "_fima_col_banner")) _fima_col_banner = -1;
//left top
if(_fima_col_banner > -1) {
	if (global.bar_w - bbox_left < 1 or global.bar_h - bbox_top < 1) {
		_fima_col_banner = -1
	} else {
		switch(_fima_col_banner) {
			case 0: x = clamp(x,global.bar_w + (64*image_xscale / 2),9999); break;
			case 1: y = clamp(y,global.bar_h + (64*image_yscale / 2),9999); break;
		}
	}
} else {
	if (global.bar_w - bbox_left > 0 and global.bar_h - bbox_top > 0) {
		if (global.bar_w - bbox_left > global.bar_h - bbox_top > 0) _fima_col_banner = 1
		else _fima_col_banner = 0
	}
}