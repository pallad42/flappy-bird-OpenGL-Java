#version 330 core

layout (location = 0) out vec4 color;

uniform sampler2D tex;
uniform int top;

in DATA {
	vec2 textureCoords;
} fs_in;

void main(void) {
	if(top == 1) {
		fs_in.textureCoords.y = 1.0 - fs_in.textureCoords.y;
	}

	color = texture(tex, fs_in.textureCoords);
	if(color.w < 1.0) discard;
}