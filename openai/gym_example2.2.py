import gym
import numpy as np

import random

env = gym.make("Taxi-v3")

print("Action Space {}".format(env.action_space))
print("State Space {}".format(env.observation_space))

# (taxi row, taxi column, passenger index, dest. index)
state = env.encode(3, 1, 2, 0)
print("State:", state)

env.s = state
env.render()

print("Reward in state 328: ", env.P[328])

q_table = np.zeros([env.observation_space.n, env.action_space.n])

# parameters
alpha = 0.1
gamma = 0.6
epsilon = 0.1

# need for graphing
all_epochs = []
all_penalties = []

for i in range(1, 10001):  # try going until 100001
    state = env.reset()

    epochs, penalties, reward, = 0, 0, 0
    done = False

    while not done:
        if random.uniform(0, 1) < epsilon:
            action = env.action_space.sample()  # Explore action space
        else:
            action = np.argmax(q_table[state])  # Exploit learned values

        next_state, reward, done, info = env.step(action)

        old_value = q_table[state, action]
        next_max = np.max(q_table[next_state])

        new_value = (1 - alpha) * old_value + alpha * (reward + gamma * next_max)
        q_table[state, action] = new_value

        if reward == -10:
            penalties += 1

        state = next_state
        epochs += 1

    if i % 100 == 0:
        print("Episode: ", i)

print("Training finished.\n")

print("Q-table of state 328: ", q_table[328])


"""Evaluate agent's performance after Q-learning"""

total_epochs, total_penalties = 0, 0
episodes = 100

for _ in range(episodes):
    state = env.reset()
    epochs, penalties, reward = 0, 0, 0

    done = False

    while not done:
        action = np.argmax(q_table[state])
        state, reward, done, info = env.step(action)

        if reward == -10:
            penalties += 1

        epochs += 1

    total_penalties += penalties
    total_epochs += epochs

print("Results after ", episodes, " episodes:")
print("Average timesteps per episode: ", total_epochs / episodes)
print("Average penalties per episode: ", total_penalties / episodes)
