const express = require('express');
const admin = require('firebase-admin');
const multer = require('multer');
const axios = require('axios');
const FormData = require('form-data');
const fs = require('fs');
const { v4: uuidv4 } = require('uuid');
const cors = require('cors');
const bcrypt = require('bcrypt');
const path = require('path');
const { promisify } = require('util');

const port = 8080;
const app = express();


// Inisialisasi Firebase Admin
const serviceAccount = require('./serviceAccountKey.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://capstone-fruisca-default-rtdb.firebaseio.com/', // Ganti dengan URL Firebase Realtime Database Anda
  storageBucket: 'gs://capstone-fruisca.appspot.com' // Ganti dengan nama Firebase Storage bucket Anda
});

const db = admin.database();
const bucket = admin.storage().bucket();

// Konfigurasi Multer
const storage = multer.memoryStorage();
const upload = multer({ storage: storage });

// Middleware untuk mengizinkan akses dari domain lain (CORS)
app.use(cors());

// Middleware untuk mengizinkan penggunaan JSON dalam body request
app.use(express.json());
app.use(express.urlencoded({ extended: true }));


// Route untuk registrasi pengguna
app.post('/register', async (req, res) => {
    try {
      const { nama, email, password, tempatTinggal, noTelepon } = req.body;
  
      // Mendeklarasikan variabel passwordHash
      //let passwordHash;
      // Meng-hash kata sandi sebelum menyimpannya
      const hashedPassword = await hashPassword(password);
      // Mendefinisikan variabel passwordHash dengan nilai hashedPassword
      passwordHash = hashedPassword;
  
      // Buat pengguna baru di Firebase Authentication
      const userRecord = await admin.auth().createUser({
        nama: nama,
        email: email,
        password: hashedPassword,
      });
  
      // Simpan informasi pengguna di Firebase Realtime Database
      const userData = {
        email: email,
        tempatTinggal: tempatTinggal,
        noTelepon: noTelepon,
        passwordHash: hashedPassword,
      };
  
      
      // Periksa setiap properti dan tambahkan ke userData jika memiliki nilai yang valid
      if (nama) {
        userData.nama = nama;
      }
      if (email) {
        userData.email = email;
      }
      if (tempatTinggal) {
        userData.tempatTinggal = tempatTinggal;
      }
      if (noTelepon) {
        userData.noTelepon = noTelepon;
      }
  
      // Simpan data ke Firebase Realtime Database
      const databaseRef = admin.database().ref('users').child(userRecord.uid);
      await databaseRef.set(userData);
  
      // await admin.database().ref('users').child(userRecord.uid).set(userData);
  
      res.status(200).json({ message: 'Registration successful', 
      data : {
        nama: nama,
        email: email,
        password: password,
        tempatTinggal: tempatTinggal,
        noTelepon: noTelepon,
      } 
    });
    } catch (error) {
      console.error('Error during registration:', error);
      res.status(500).json({ message: 'Registration failed' });
    }
  });
  

  // Fungsi untuk meng-hash kata sandi
  async function hashPassword(password) {
    try {
      const saltRounds = 10;
      const salt = await bcrypt.genSalt(saltRounds);
      const hashedPassword = await bcrypt.hash(password, salt);
      return hashedPassword;
    } catch (error) {
      console.error('Error:', error);
      throw new Error('Password hashing failed');
    }
  }
  
  // Fungsi untuk memverifikasi kata sandi
  async function verifyPassword(password, passwordHash) {
    try {
      if (!password || !passwordHash) {
        throw new Error('Data and hash arguments are required');
      }
  
      const isPasswordValid = await bcrypt.compare(password, passwordHash);
      return isPasswordValid;
    } catch (error) {
      console.error('Error:', error);
      throw error;
    }
  }
  
  
  // Validasi format email
  function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }
  
  
  
  
  /// Route untuk login pengguna
  app.post('/login', async (req, res) => {
    try {
      const { email, password } = req.body;
  
      // Validasi format email
      if (!validateEmail(email)) {
        res.status(400).json({ message: 'Invalid email format' });
        return;
      }
  
      // Ambil pengguna berdasarkan email
      let userRecord;
      try {
        userRecord = await admin.auth().getUserByEmail(email);
      } catch (error) {
        console.error('Error getting user:', error);
        res.status(404).json({ message: 'User not found' });
        return;
      }
  
      const { displayName, email: userEmail, uid } = userRecord;
      
      // Ambil data pengguna dari Firebase Realtime Database
      const snapshot = await admin.database().ref('users').child(uid).once('value');
      const userData = snapshot.val();
  
      if (!password || !userData || !userData.passwordHash) {
        res.status(400).json({ message: 'Invalid request' });
        return;
      }
  
  
      // Memperbarui userData dengan displayName dari userRecord
      userData.displayName = displayName;
      
      const isPasswordValid = await verifyPassword(password, userData.passwordHash);
  
      if (isPasswordValid) {
        // Login berhasil
        console.log('Login successful');
  
        res.status(200).json({
          message: 'Login successful',
          data: {
            uid: uid,
            nama: displayName,
            email: userEmail,
          },
        });
      } 
      else {
        // Login gagal
        console.log('Password salah');
        res.status(401).json({ message: 'Password salah' });
      }
    } catch (error) {
      console.error('Error during login:', error);
      res.status(500).json({ message: 'Login failed' });
    }
  });
  
  


  // Route untuk melihat semua data pengguna
  app.get('/users', async (req, res) => {
    try {
      const snapshot = await admin.database().ref('users').once('value');
      const users = snapshot.val();
  
      // Membuat array untuk menyimpan data pengguna dengan nama dan email
      const usersData = [];
  
      // Loop melalui pengguna dan menambahkan data ke array
      Object.keys(users).forEach((userId) => {
        const userData = users[userId];
  
        // Memeriksa apakah ada properti 'nama' dan 'email'
        if (userData.nama && userData.email) {
          usersData.push({
            uid: userId,
            nama: userData.nama,
            email: userData.email,
            tempatTinggal: userData.tempatTinggal,
            noTelepon: userData.noTelepon,
          });
        }
      });
  
      console.log('get users is success');
      res.status(200).json(users);
    } catch (error) {
      console.error('Error getting users:', error);
      res.status(500).json({ message: 'Failed to get users' });
    }
  });
  


  
  // Endpoint untuk mendapatkan profil pengguna beserta URL foto profil
  app.get('/profile/:userId', async (req, res) => {
    try {
      const userId = req.params.userId;
  
      // Mendapatkan data pengguna dari Realtime Database
      const snapshot = await admin.database().ref('users/' + userId).once('value');
      const profile = snapshot.val();
  
      if (profile) {
        if (profile.displayName) {
          console.log('Nama:', profile.displayName);
        } else {
          console.log('Profil pengguna');
        }
  
        // Menggabungkan URL foto profil jika tersedia
        if (profile.profilePhotoUrl) {
          profile.profilePhotoUrl = `${req.protocol}://${req.get('host')}/${profile.profilePhotoUrl}`;
        }
  
        res.status(200).json(profile);
      } else {
        console.log('User not found');
        res.status(404).json({ message: 'User not found' });
      }
    } catch (error) {
      console.error('Error getting user profile:', error);
      res.status(500).json({ message: 'Failed to get user profile' });
    }
  });
  
  
  

  // Endpoint untuk mengunggah foto profil pengguna
  app.post('/upload-profile-photo/:userId', upload.single('profilePhoto'), async (req, res) => {
    try {
      const userId = req.params.userId;
      const file = req.file;
  
      if (!file) {
        res.status(400).json({ message: 'No file uploaded' });
        return;
      }
  
      // Mendapatkan ekstensi file
      const fileExt = path.extname(file.originalname);
  
      // Membuat nama unik untuk file foto profil
      const filename = `${uuidv4()}${fileExt}`;
  
      // Menentukan jalur file di Firebase Storage
      const filePath = `profil/${filename}`;
  
      // Mengunggah file ke Firebase Storage
      const uploadFile = bucket.file(filePath);
  
      await promisify(uploadFile.save).bind(uploadFile)(file.buffer, { contentType: file.mimetype });
  
      // Mendapatkan URL file yang diunggah
      const downloadUrl = await promisify(uploadFile.getSignedUrl).bind(uploadFile)({
        action: 'read',
        expires: '03-09-2500', // Atur tanggal kedaluwarsa URL sesuai kebutuhan
      });
  
      // Memperbarui data pengguna dengan URL foto profil baru
      await admin.database().ref('users').child(userId).update({ profilePhotoUrl: downloadUrl[0] });
  
      res.status(200).json({ message: 'Profile photo uploaded successfully', downloadUrl: downloadUrl[0] });
    } catch (error) {
      console.error('Error during profile photo upload:', error);
      res.status(500).json({ message: 'Profile photo upload failed' });
    }
  });
  
  
  
  
  // Route untuk mereset password pengguna
  app.post('/reset-password', async (req, res) => {
    try {
      const { email, password, newPassword } = req.body;
  
      // Validasi format email
      if (!validateEmail(email)) {
        res.status(400).json({ message: 'Invalid email format' });
        return;
      }
  
      // Ambil pengguna berdasarkan email
      let userRecord;
      try {
        userRecord = await admin.auth().getUserByEmail(email);
      } catch (error) {
        console.error('Error getting user:', error);
        res.status(404).json({ message: 'User not found' });
        return;
      }
  
      const { uid } = userRecord;
  
      // Meng-hash kata sandi baru
      const newHashedPassword = await hashPassword(newPassword);
  
      // Memperbarui kata sandi di Firebase Authentication
          await admin.auth().updateUser(uid, { password: newHashedPassword });
  
      // Ambil data pengguna dari Firebase Realtime Database
      const snapshot = await admin.database().ref('users').child(uid).once('value');
      const userData = snapshot.val();
  
  
      // Memperbarui kata sandi di Firebase Realtime Database
      await admin.database().ref('users').child(uid).update({ passwordHash: newHashedPassword });
  
      // Memperbarui data pengguna yang diperbarui dengan UID baru
      userData.uid = uid;
  
      // Tampilkan data pengguna yang diperbarui di Visual Studio Code
      console.log('Password reset successful');
  
      res.status(200).json({ 
        message: 'Password reset successful',
        data: userData
      });
    } catch (error) {
      console.error('Error during password reset:', error);
      res.status(500).json({ message: 'Password reset failed' });
    }
  });
  



  // Route untuk logout pengguna
  app.post('/logout', async (req, res) => {
    try {
      // Mendapatkan token akses dari header Authorization
      const authorizationHeader = req.headers.authorization;
      const accessToken = authorizationHeader ? authorizationHeader.split(' ')[1] : null;
  
      // Memvalidasi token akses
      if (!accessToken) {
        res.status(401).json({ message: 'Unauthorized' });
        return;
      }
  
      // Memutakhirkan token akses di Firebase Authentication
      await admin.auth().revokeRefreshTokens(accessToken);
  
      // Menghapus token akses dari Firebase Realtime Database
      const tokenRef = admin.database().ref('tokens').child(accessToken);
      await tokenRef.remove();

      // Menampilkan informasi pengguna di terminal Visual Studio
      console.log('User logged out');
  
      res.status(200).json({ message: 'Logout successful' });
    } catch (error) {
      console.error('Error during logout:', error);
      res.status(500).json({ message: 'Logout failed' });
    }
  });




// Endpoint untuk upload foto dan melakukan scan
app.post('/upload', upload.single('image'), (req, res) => {
  // Dapatkan gambar yang diupload dari `req.file.buffer`
  const imageBuffer = req.file.buffer;

  // Simpan hasil scan ke Firebase Storage
  const bucket = admin.storage().bucket();
  const fileName = `images/${Date.now()}_${req.file.originalname.replace(/[^a-zA-Z0-9.]/g, '')}`;
  const file = bucket.file(fileName);
  const fileStream = file.createWriteStream({
    metadata: {
      contentType: req.file.mimetype,
    },
  });
  fileStream.on('error', (error) => {
    console.error('Error uploading file:', error);
    res.sendStatus(500);
  });
  fileStream.on('finish', () => {
    // Simpan data hasil scan ke Firebase Realtime Database
    const db = admin.database();
    const scansRef = db.ref('scans');
    const newScanRef = scansRef.push();
    newScanRef
      .set({
        imageUrl: `gs://${bucket.name}/${fileName}`,
        timestamp: admin.database.ServerValue.TIMESTAMP,
      })
      .then(() => {
        // Kirim permintaan HTTP ke endpoint model ML yang telah di-deploy di Cloud Run
        const modelUrl = 'https://capstone-fruisca-taqxnmgvca-uc.a.run.app/predict_image'; // Ganti dengan URL endpoint Cloud Run yang telah Anda deploy
        const formData = new FormData();
        formData.append('file', imageBuffer, `${fileName}`);

        axios
          .post(modelUrl, formData, {
            headers: formData.getHeaders(),
          })
          .then((response) => {
            // Menangani respons dari ML model
            const result = response.data.result;
            console.log('Prediction result:', result);

            // Tambahkan hasil scan ke data history
            const historyData = {
              id: newScanRef.key,
              imageUrl: `gs://${bucket.name}/${fileName}`,
              timestamp: new Date().getTime(),
              result: result,
            };

            db.ref('history').push(historyData)
              .then(() => {
                res.status(200).json({ result }); // Mengirimkan hasil prediksi ke klien
              })
              .catch((error) => {
                console.error('Error saving scan data to history:', error);
                res.sendStatus(500);
              });
          })
          .catch((error) => {
            console.error('Error calling ML model endpoint:', error);
            res.sendStatus(500);
          });
      })
      .catch((error) => {
        console.error('Error saving scan data:', error);
        res.sendStatus(500);
      });
  });
  fileStream.end(imageBuffer); // Simpan gambar yang diupload ke Firebase Storage
});




// Endpoint untuk mendapatkan data history scan
app.get('/history', (req, res) => {
  const db = admin.database();
  const historyRef = db.ref('history');
  historyRef
    .once('value')
    .then((snapshot) => {
      const history = [];
      snapshot.forEach((childSnapshot) => {
        const key = childSnapshot.key;
        const data = childSnapshot.val();
        history.push({
          id: key,
          imageUrl: data.imageUrl,
          timestamp: new Date(data.timestamp),
          result: data.result,
        });
      });
      res.json(history);
    })
    .catch((error) => {
      console.error('Error fetching history:', error);
      res.sendStatus(500);
    });
});



// Jalankan server
app.listen(port, () => {
  console.log(`Server berjalan di http://localhost:${port}`);
});