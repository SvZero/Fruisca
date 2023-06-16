import React, { useState } from 'react';
import { View, Button, Image } from 'react-native';
import ImagePicker from 'react-native-image-picker';

const App = () => {
  const [imageUri, setImageUri] = useState(null);

  const handleChooseImage = () => {
    const options = {
      title: 'Select Image',
      mediaType: 'photo',
      quality: 0.5,
    };

    ImagePicker.launchCamera(options, (response) => {
      if (response.didCancel) {
        console.log('User cancelled image picker');
      } else if (response.error) {
        console.log('Image picker error:', response.error);
      } else if (response.uri) {
        // Mengirim gambar ke server saat dipilih
        uploadImage(response);
      }
    });
  };

  const uploadImage = (image) => {
    const formData = new FormData();
    formData.append('image', {
      uri: image.uri,
      type: image.type,
      name: image.fileName || 'image.jpg',
    });

    fetch('http://localhost:8080/upload', {
      method: 'POST',
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        // Mendapatkan URL gambar dari respons server
        const imageUrl = data.imageUrl;
        setImageUri(imageUrl);
      })
      .catch((error) => {
        console.error('Error uploading image:', error);
      });
  };

  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      {imageUri && <Image source={{ uri: imageUri }} style={{ width: 200, height: 200 }} />}
      <Button title="Choose Image" onPress={handleChooseImage} />
    </View>
  );
};

export default App;
