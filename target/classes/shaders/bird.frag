#version 330 core

layout (location = 0) out vec4 color;

uniform sampler2D tex;

in DATA {
	vec2 textureCoords;
} fs_in;

void main(void) {
	color = texture(tex, fs_in.textureCoords);
	if(color.w < 1.0) discard;
}