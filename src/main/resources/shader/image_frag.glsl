#version 330

out vec4 color;

in vec2 texCoordinates;
//in vec3 textire_coordinates;

uniform sampler2D ourTexture;

void main() {

    color = texture(ourTexture, texCoordinates);
//    color=vec4(textire_coordinates,1.0);
}
