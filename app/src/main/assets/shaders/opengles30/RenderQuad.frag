#version 300 es

uniform sampler2D uTexture;

in vec2 vTexCoords;

out vec4 fragmentColor;

void main() {
    fragmentColor = texture(uTexture, vTexCoords);

    //fragmentColor = vec4(vec3(1.0 - texture2D(uTexture, vTexCoords)), 1.0);
}