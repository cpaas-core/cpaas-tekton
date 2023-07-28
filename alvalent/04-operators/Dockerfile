FROM python:3.11
WORKDIR /app
COPY requirements.txt /app/
RUN pip install -r requirements.txt
COPY cronprint_controller.py /app/
CMD kopf run /app/cronprint_controller.py --verbose