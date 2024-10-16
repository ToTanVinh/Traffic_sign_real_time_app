from fastapi import FastAPI, UploadFile, File
from fastapi.responses import StreamingResponse
import torch
import cv2
import numpy as np
from io import BytesIO
from PIL import Image
import uvicorn

# Initialize FastAPI app
app = FastAPI()

# Load the YOLOv5 model
model = torch.hub.load('ultralytics/yolov5', 'custom', path='last.pt')

@app.post("/predict/")
async def predict(file: UploadFile = File(...)):
    # Read image file
    contents = await file.read()
    np_img = np.frombuffer(contents, np.uint8)
    img = cv2.imdecode(np_img, cv2.IMREAD_COLOR)
    
    # Perform inference
    results = model(img)
    
    # Convert the results image to a PIL Image
    results_img = results.render()[0]  # Render the results on the image
    pil_img = Image.fromarray(results_img)

    # Prepare image to return as a PNG
    buffer = BytesIO()
    pil_img.save(buffer, format="PNG")
    buffer.seek(0)

    return StreamingResponse(buffer, media_type="image/png")

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
