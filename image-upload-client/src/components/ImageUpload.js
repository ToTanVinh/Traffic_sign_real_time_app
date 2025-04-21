import React, { useState } from "react";
import axios from "axios";
import "./ImageUploadCSS.css";
const ImageUpload = () => {
  const [image, setImage] = useState(null);

  const handleUpload = async (event) => {
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/upload",
        formData,
        {
          responseType: "arraybuffer", // Chỉ định kiểu phản hồi là arraybuffer
        }
      );

      // Chuyển đổi phản hồi thành URL hình ảnh
      const imageBlob = new Blob([response.data], { type: "image/png" });
      const imageUrl = URL.createObjectURL(imageBlob);
      setImage(imageUrl); // Lưu URL vào state
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  };

  return (
    <div>
      <input type="file" onChange={handleUpload} />
      {image && <img src={image} alt="Uploaded" className="uploaded-image" />}
    </div>
  );
};

export default ImageUpload;
