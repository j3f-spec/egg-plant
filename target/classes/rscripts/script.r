install.packages(c("tidyverse", "ggplot2", "plotly", "lubridate"))

library(tidyverse)
library(ggplot2)
library(plotly)
library(lubridate)

car_data <- read.csv("/content/Car_sales.csv")

head(car_data)