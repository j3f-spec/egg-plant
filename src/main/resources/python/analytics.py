import pandas as pd
import json

df = pd.read_csv("src/main/resources/data/eggplant_sales_data.csv")

result = {
    "rows": len(df),
    "columns": list(df.columns)
}

print(json.dumps(result))