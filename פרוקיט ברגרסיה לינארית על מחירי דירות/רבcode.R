
install.packages("corrplot")
install.packages("ggcorrplot")
install.packages("fBasics")
install.packages("incidence")
install.packages("ggplot2")
install.packages("strucchange")
install.packages("sqldf")
install.packages("SciViews")

#libraries
library(ggplot2)
library(moments)
library(mosaic)
library(vcd)
library(dplyr)
library("plot3D")
library("scatterplot3d") 
library('plyr')
library(gmodels)
library(magrittr)
library(rlang)
library(MASS)
library(fitdistrplus)
library(magrittr)
library(dplyr)
library(lazyeval)
library(parallel)
library(e1071)
library(plotly)
library(ggplot2)
library(triangle)
library(sqldf)
library(simmer)
library(simmer.plot)
library(corrplot)
library(gridExtra)
library(strucchange)
library(SciViews)

# Loading the data
dataset<-read.csv(file.choose(),header = T)
dataset2<-subset(dataset, select = c("renovated","rooms","sqft_house","sqft_lot","floors","yr_built","sqft_basement","condition","price"))

#--------Q2.1
#-------- corelation table

DT_4_COR<-cor(dataset2)

#-------- first variable year built correlation to price

plot(x=dataset$yr_built,y=dataset$price,
     xlab="year built",ylab="price",main="Regression for year built and price",col = "red")
fit_year_price<-lm(formula = price ~ yr_built, data = dataset)
abline(fit_year_price,col = "blue")
summary(fit_year_price)

#-------- second variable condition correlation to price
condition_categories<- dataset$condition<-  as.factor(dataset$condition)
p <- ggplot(dataset, aes(x=condition_categories, y=price
                         ,main="boxplot by condition category and price")) + 
  geom_boxplot()
p+geom_jitter(shape=16, position=position_jitter(0.2), col = "blue")

fit_condition_price<-lm(formula = price ~ condition, data = dataset)
summary(fit_condition_price)



#------- third variable floors correlation to price

floors_categories<- dataset$floors<-  as.factor(dataset$floors)
p <- ggplot(dataset, aes(x=floors_categories, y=price
                         ,main="boxplot by floors category and price")) + 
  geom_boxplot()
p+geom_jitter(shape=16, position=position_jitter(0.2), col = "red")


fit_floors_price<-lm(formula = price ~ floors, data = dataset)
summary(fit_floors_price)

#------- fourth variable rooms correlation to price
rooms_categories<- dataset$rooms<-  as.factor(dataset$rooms)
p <- ggplot(dataset, aes(x=rooms_categories, y=price
                         ,main="boxplot by rooms category and price")) + 
  geom_boxplot()
p+geom_jitter(shape=16, position=position_jitter(0.2), col = "green")


fit_rooms_price<-lm(formula = price ~ rooms, data = dataset)
summary(fit_rooms_price)

#------------Q2.2

dataset$Csqft_house <- cut(dataset$sqft_house,
              breaks=c(0, 1160, 2010, 3000, 5420),
              labels=c('studio', 'single family', 'villa', 'mansion'))


dataset$rooms <- cut(dataset2$rooms,
                            breaks=c(0, 2, 3, 4,6),
                            labels=c('2', '3', '4', '5+'))


#------------Q2.3

x1 <- dataset$rooms
x2 <- dataset$sqft_house
x3 <- dataset$sqft_lot
x4 <- dataset$sqft_basement
x5 <- dataset$floors
x6 <- dataset$condition
x7 <- dataset$yr_built
x8 <- dataset$renovated
y  <- dataset$price


#create dummy variables
house_size_v1 <- ifelse(x2 == "studio", 1, 0)
house_size_v2 <- ifelse(x2 == "single family", 1, 0)
house_size_v3 <- ifelse(x2 == "villa", 1, 0)
house_size_v4 <- ifelse(x2 == "mansion", 1, 0)

room_num_v1 <- ifelse(x2 == "2", 1, 0)
room_num_v2 <- ifelse(x2 == "3", 1, 0)
room_num_v3 <- ifelse(x2 == "4", 1, 0)
room_num_v4 <- ifelse(x2 == "5+", 1, 0)

floor_num_v1 <- ifelse(x2 == "1", 1, 0)
floor_num_v2 <- ifelse(x2 == "2", 1, 0)
floor_num_v3 <- ifelse(x2 == "3", 1, 0)


#------------Q2.4

## renovated - lot size
dataset$renovatedcolor <- 
  ifelse(dataset$renovated==TRUE,'red','blue')




fitx8_x4_x3reno<-lm(sqldf("select * from dataset where renovated = '1'")$price ~ sqldf("select * from dataset where renovated = '1'")$sqft_lot)
fitx8_x4_x3NOTreno<-lm(sqldf("select * from dataset where renovated = '0'")$price ~ sqldf("select * from dataset where renovated = '0'")$sqft_lot)
abline(fitx8_x4_x3reno,lwd=2,col="red")
abline(fitx8_x4_x3NOTreno,lwd=2,col="blue")

fitx3x8 <- lm(y ~ x8*x3 )
summary(fitx3x8)

## house type - lot size

dataset$housecolor <- 
  ifelse(dataset$Csqft_house=='studio','red',
         ifelse(dataset$Csqft_house=='single family','green',
                ifelse(dataset$Csqft_house=='villa','purple',
                       ifelse(dataset$Csqft_house=='mansion','blue',
                'yellow'))))


plot(axes = 2,x = dataset$sqft_lot, y = dataset$price, xlab = "lot size", ylab = "price", col= dataset$housecolor)
legend(x = 10, y = 4e5+1900000, legend=c('studio' , 'single family','villa','mansion'),
       col=c("red", "green","purple","blue"), lty=1, cex=0.5,
       title="house type", text.font=3)

abline(lm(sqldf("select * from dataset where Csqft_house = 'studio'")$price ~ sqldf("select * from dataset where Csqft_house = 'studio'")$sqft_lot),lwd=2,col="red")
abline(lm(sqldf("select * from dataset where Csqft_house = 'single family'")$price ~ sqldf("select * from dataset where Csqft_house = 'single family'")$sqft_lot),lwd=2,col="green")
abline(lm(sqldf("select * from dataset where Csqft_house = 'villa'")$price ~ sqldf("select * from dataset where Csqft_house = 'villa'")$sqft_lot),lwd=2,col="purple")
abline(lm(sqldf("select * from dataset where Csqft_house = 'mansion'")$price ~ sqldf("select * from dataset where Csqft_house = 'mansion'")$sqft_lot),lwd=2,col="blue")

fitx3x2 <- lm(y ~ dataset$Csqft_house*x3 )
summary(fitx3x2)

##  floors - lot size

dataset$floorscolor <- 
  ifelse(dataset$floors==1,'red',
         ifelse(dataset$floors==2,'green',
                ifelse(dataset$floors==3,'purple'
                     ,'yellow')))


plot(axes = 2,x = dataset$sqft_lot, y = dataset$price, xlab = "lot size", ylab = "price", col= dataset$floorscolor)
legend(x = 10, y = 4e5+1900000, legend=c('1' , '2 ','3'),
       col=c("red", "green","purple"), lty=1, cex=0.5,
       title="num floors", text.font=3)

abline(lm(sqldf("select * from dataset where floorscolor = 'red'")$price ~ sqldf("select * from dataset where floorscolor = 'red'")$sqft_lot),lwd=2,col="red")
abline(lm(sqldf("select * from dataset where floorscolor = 'green'")$price ~ sqldf("select * from dataset where floorscolor = 'green'")$sqft_lot),lwd=2,col="green")
abline(lm(sqldf("select * from dataset where floorscolor = 'purple'")$price ~ sqldf("select * from dataset where floorscolor = 'purple'")$sqft_lot),lwd=2,col="purple")


fitx3x5 <- lm(y ~ dataset$floors*x3 )
summary(fitx3x5)

## condition - sqft_basment

dataset$conditioncolor <- 
  ifelse(dataset$condition==1,'red',
         ifelse(dataset$condition==2,'green',
                ifelse(dataset$condition==3,'purple',
                       ifelse(dataset$condition==4,'blue',
                             ifelse(dataset$condition==5,'black','yellow')))))


plot(axes = 2,x = dataset$sqft_basement, y = dataset$price, xlab = "basement size", ylab = "price", col= dataset$conditioncolor)
legend(x = 10, y = 4e5+1900000, legend=c('1' , '2','3','4','5'),
       col=c("red", "green","purple","blue","black"), lty=1, cex=0.5,
       title="condition", text.font=3)

#abline(lm(sqldf("select * from dataset where conditioncolor = 'red'")$price ~ sqldf("select * from dataset where conditioncolor = 'red'")$sqft_basement),lwd=2,col="red")
#abline(lm(sqldf("select * from dataset where conditioncolor = 'green'")$price ~ sqldf("select * from dataset where conditioncolor = 'green'")$sqft_basement),lwd=2,col="green")
abline(lm(sqldf("select * from dataset where conditioncolor = 'purple'")$price ~ sqldf("select * from dataset where conditioncolor = 'purple'")$sqft_basement),lwd=2,col="purple")
abline(lm(sqldf("select * from dataset where conditioncolor = 'blue'")$price ~ sqldf("select * from dataset where conditioncolor = 'blue'")$sqft_basement),lwd=2,col="blue")
abline(lm(sqldf("select * from dataset where conditioncolor = 'black'")$price ~ sqldf("select * from dataset where conditioncolor = 'black'")$sqft_basement),lwd=2,col="black")

fitx4x6 <- lm(y ~ dataset$conditioncolor*x4 )
summary(fitx4x6)

############Q3
dataset3<- subset(dataset, select = c("renovated","rooms","sqft_house","Csqft_house","sqft_lot","floors","sqft_basement","condition","price"))
########### creating dummy variables for condition

dummy_condition_almost_good <- ifelse(x6 == 2, 1, 0)
dummy_condition_good <- ifelse(x6 == 3, 1, 0)
dummy_condition_almost_verygood <- ifelse(x6 == 4, 1, 0)
dummy_condition_verygood <- ifelse(x6 == 5, 1, 0)
########### creating dummy variables for renovated

dummy_renovated <- ifelse(x8 == 1, 1, 0)
summary(dummy_renovated)

########### creating dummy variables for sqft house

dummy_house_single_family <- ifelse(dataset3$Csqft_house == "single family", 1, 0)
dummy_house_villa <- ifelse(dataset3$Csqft_house == "villa", 1, 0)
dummy_house_mansion <- ifelse(dataset3$Csqft_house == "mansion", 1, 0)

########### creating dummy variables for rooms

dummy_rooms_3 <- ifelse(dataset3$rooms == "3", 1, 0)
dummy_rooms_4 <- ifelse(dataset3$rooms == "4", 1, 0)
dummy_rooms_5 <- ifelse(dataset3$rooms == "5+", 1, 0)

########### creating dummy variables for floors

dummy_floors_2 <- ifelse(dataset3$floors == 2, 1, 0)
dummy_floors_3 <- ifelse(dataset3$floors == 3, 1, 0)


#first we calculate the full model with all parameters that we decide to remain 
fullModel<-lm(y~ x1+x2+x3+x4+x5+x6+x8+dummy_floors_3*x3+dummy_floors_3+dummy_house_mansion+
                dummy_renovated*x3 )
summary(fullModel)
AIC(fullModel)
BIC(fullModel)

#Empty model
Emp <- lm (price ~ 1,data=dataset3)
summary(Emp) 
AIC(Emp)
BIC(Emp)


#backwards
fit_backwards <- step(lm(fullModel, data = dataset3) ,direction = "backward",scope=~1 , test="F")
summary(fit_backwards)
AIC(fit_backwards)
BIC(fit_backwards)


#forward
fit_forwards <- step(lm(y ~ 1, data = dataset3) ,direction = "forward", scope = formula(fullModel), test="F")
summary(fit_forwards)
AIC(fit_forwards)
BIC(fit_forwards)


#both
fit_stepwise <- step(lm(fullModel,data = dataset3) ,direction = "both", test="F")
summary(fit_stepwise)
AIC(fit_stepwise)
BIC(fit_stepwise)


###### 3.2
##Normal statistic test
model <- lm(formula = y ~ x2 + x3 + dummy_floors_3 + dummy_renovated + x3:dummy_floors_3 + 
              x3:dummy_renovated)
summary(model)
dataset$fitted <- fitted(model)
dataset$residuals <- residuals(model)
s.e_res <- sqrt(var(dataset$residuals))
dataset$stan_residuals <- (residuals(model)/s.e_res)
qqnorm (dataset$stan_residuals)
abline( a=0, b=1, col = "red")
hist(dataset$stan_residuals, prob=TRUE, xlab = "Normalized error", ylab = "Frequency", col = "white")
lines(density(dataset$stan_residuals), col = "red", lwd = 2)
#ks
ks.test( x= dataset$stan_residuals, y = "pnorm", alternative = "two.sided", exact = NULL)
#shapiro Wilk
shapiro.test(dataset$stan_residuals)


#Equality of variance
model <- lm(fit_backwards)
predicted <- predict(model)
ust_residuals <- resid(model)
residuals <- (ust_residuals-mean(ust_residuals)/sd(ust_residuals))
plot(predicted,residuals,main="residuals vs. fit",xlab="predicted value",ylab="error")
abline(0,0)


#print(dataset_Chow)
sctest (y ~ x2 + x3 + dummy_floors_3 + dummy_renovated + x3:dummy_floors_3 + 
          x3:dummy_renovated, type = "Chow", point = 10)

#---------------------------------------Q4
par(mfrow=c(1,1))
Themodel.boxcox <- boxcox(model)
title((main = "Box-Cox"))
lamda <-Themodel.boxcox$x[which.max(Themodel.boxcox$y)]
lamda



#the model after the transformation of ln(y)
the_model_after_trans <- lm(ln(y)~ x2 + x3 + dummy_floors_3 + dummy_renovated + x3:dummy_floors_3 + 
                              x3:dummy_renovated, data = dataset3)
summary(the_model_after_trans)

AIC(the_model_after_trans)
newmodel <- step(lm(ln(y) ~ 1, data = dataset3) ,direction = "forward", scope = formula(the_model_after_trans), test="F")
summary(newmodel)
AIC(newmodel)
#------------------------------Linear check for transformation
sctest (newmodel, type = "Chow", point = 10)


##Normal statistic test
model <- lm(newmodel)
summary(model)
dataset$fitted <- fitted(model)
dataset$residuals <- residuals(model)
s.e_res <- sqrt(var(dataset$residuals))
dataset$stan_residuals <- (residuals(model)/s.e_res)
qqnorm (dataset$stan_residuals)
abline( a=0, b=1, col = "red")
hist(dataset$stan_residuals, prob=TRUE, xlab = "Normalized error", ylab = "Frequency", col = "white")
lines(density(dataset$stan_residuals), col = "red", lwd = 2)


#shapiro Wilk
shapiro.test(dataset$stan_residuals)

#Equality of variance
model <- lm(the_model_after_trans)
predicted <- predict(model)
ust_residuals <- resid(model)
residuals <- (ust_residuals-mean(ust_residuals)/sd(ust_residuals))
plot(predicted,residuals,main="residuals vs. fit",xlab="predicted value",ylab="error")
abline(0,0)


#-------------------------cheCking other transformations sqrt(y)

the_model_after_trans <- lm(sqrt(y)~ x2 + x3 + dummy_floors_3 + dummy_renovated + x3:dummy_floors_3 + 
                              x3:dummy_renovated, data = dataset3)
summary(the_model_after_trans)

AIC(the_model_after_trans)
newmodel <- step (the_model_after_trans, dirction = 'backward', scope =~1)
summary(newmodel)
AIC(newmodel)

#------------------------------Linear check for transformation
sctest (newmodel, type = "Chow", point = 10)
##Normal statistic test
model <- lm(newmodel)
summary(model)
dataset$fitted <- fitted(model)
dataset$residuals <- residuals(model)
s.e_res <- sqrt(var(dataset$residuals))
dataset$stan_residuals <- (residuals(model)/s.e_res)
qqnorm (dataset$stan_residuals)
abline( a=0, b=1, col = "red")
hist(dataset$stan_residuals, prob=TRUE, xlab = "Normalized error", ylab = "Frequency", col = "white")
lines(density(dataset$stan_residuals), col = "red", lwd = 2)
#shapiro Wilk
shapiro.test(dataset$stan_residuals)
#Equality of variance
model <- lm(the_model_after_trans)
predicted <- predict(model)
ust_residuals <- resid(model)
residuals <- (ust_residuals-mean(ust_residuals)/sd(ust_residuals))
plot(predicted,residuals,main="residuals vs. fit",xlab="predicted value",ylab="error")
abline(0,0)

#-------------------------cheCking other transformations y^2

the_model_after_trans <- lm(y^2~ x2 + x3 + dummy_floors_3 + dummy_renovated + x3:dummy_floors_3 + 
                              x3:dummy_renovated, data = dataset3)
summary(the_model_after_trans)

AIC(the_model_after_trans)
newmodel <- step (the_model_after_trans, dirction = 'backward', scope =~1)
summary(newmodel)

#------------------------------Linear check for transformation
sctest (newmodel, type = "Chow", point = 10)
##Normal statistic test
model <- lm(newmodel)
summary(model)
dataset$fitted <- fitted(model)
dataset$residuals <- residuals(model)
s.e_res <- sqrt(var(dataset$residuals))
dataset$stan_residuals <- (residuals(model)/s.e_res)
qqnorm (dataset$stan_residuals)
abline( a=0, b=1, col = "red")
hist(dataset$stan_residuals, prob=TRUE, xlab = "Normalized error", ylab = "Frequency", col = "white")
lines(density(dataset$stan_residuals), col = "red", lwd = 2)
#shapiro Wilk
shapiro.test(dataset$stan_residuals)
#Equality of variance
model <- lm(the_model_after_trans)
predicted <- predict(model)
ust_residuals <- resid(model)
residuals <- (ust_residuals-mean(ust_residuals)/sd(ust_residuals))
plot(predicted,residuals,main="residuals vs. fit",xlab="predicted value",ylab="error")
abline(0,0)






