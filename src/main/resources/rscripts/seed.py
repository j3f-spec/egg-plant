import pandas as pd 
import numpy as np 
from datetime import datetime, timedelta

np.random.seed(42)

#createdAt
start_date = datetime(2025, 3, 1)
dates = [start_date + timedelta(days=i) for i in range(90)]

#description
farms = ['Farm_A','Farm_B','Farm_C','Farm_D','Farm_E']
grades = ['Premium','Grade A','Grade B','Grade C']
regions = ['North','South','West','East']
buyers = ['Wholesale_Mart','FreshMart','SuperVeg','Export_Co','Local_Market','Organic_Store']

data = []

for i in range(500):
    date = np.random.choice(dates)
    farm_id = np.random.choice(farms)
    grade = np.random.choice(grades)

    quantity_kg = round(np.random.uniform(50, 1200), 1)
    base_price = {'Premium':85, 'Grade A': 70, 'Grade B':45,'Grade C':28}
    price_per_kg = round(base_price[grade] + np.random.uniform(-8, 12), 2)
    total_revenue = round(quantity_kg * price_per_kg, 2)

    #customer
    buyer_name = np.random.choice(buyers)
    region = np.random.choice(regions)
    transport_cost = round(np.random.uniform(80, 650), 2)
    rejection_rate = round(np.random.uniform(0.5, 18), 2)
    avg_weight_g = round(np.random.uniform(80, 350), 1)
    color_score = round(np.random.uniform(6.5, 9.9), 1)
    weather_temp = round(np.random.uniform(18, 38), 1)
    harvest_batch = f"H{date.strftime('%Y%m')}-{np.random.randint(1,50):02d}"

    data.append({
        'sale_id': f"SALE_{10000+i}",
        'date': date.strftime('%Y-%m-%d'),
        'farm_id': farm_id,
        'grade':grade,
        'quantity_kg': quantity_kg,
        'price_per_kg': price_per_kg,
        'total_revenue': total_revenue,
        'buyer_name': buyer_name,
        'region':region,
        'transport_cost': transport_cost,
        'rejection_rate': rejection_rate,
        'avg_weight-g': avg_weight_g,
        'color_score': color_score,
        'weather_temp':weather_temp,
        'harvest_batch':harvest_batch
    })

df = pd.DataFrame(data)
df = df.sort_values('date').reset_index(drop=True)
df.to_csv('eggplant_sales_data.csv', index=False)
print("CSV file generated successfully! Total rows:", len(df))