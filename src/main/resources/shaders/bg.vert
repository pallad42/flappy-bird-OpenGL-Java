#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;

uniform mat4 projection_matrix;
uniform mat4 viewmodel_matrix;

out DATA {
	vec2 textureCoords;
} vs_out;

void main(void) {
	gl_Position = projection_matrix * viewmodel_matrix * vec4(position, 1.0);
	vs_out.textureCoords = textureCoords;
}