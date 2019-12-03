#version 330

out vec4 color;

in vec2 textire_coordinates;

uniform sampler2D ourTexture;

void main() {

    color = texture(ourTexture, textire_coordinates);
}
